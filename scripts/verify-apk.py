#!/usr/bin/env python3
"""Verify that an APK is a minimal, permission-free Legacy Xposed module."""

from __future__ import annotations

import argparse
import struct
import sys
import zipfile
from pathlib import Path


ENTRY_CLASS = "io.github.fr24zh.localizer.Fr24LocalizationModule"
ENTRY_DESCRIPTOR = "Lio/github/fr24zh/localizer/Fr24LocalizationModule;"
XPOSED_DESCRIPTOR_PREFIX = "Lde/robv/android/xposed/"


class VerificationError(Exception):
    """Raised when the APK does not meet the module packaging contract."""


def read_u32(data: bytes, offset: int) -> int:
    if offset < 0 or offset + 4 > len(data):
        raise VerificationError("invalid DEX table offset")
    return struct.unpack_from("<I", data, offset)[0]


def skip_uleb128(data: bytes, offset: int) -> int:
    for _ in range(5):
        if offset >= len(data):
            raise VerificationError("truncated DEX string length")
        value = data[offset]
        offset += 1
        if value & 0x80 == 0:
            return offset
    raise VerificationError("invalid DEX string length")


def read_dex_string(data: bytes, offset: int) -> str:
    start = skip_uleb128(data, offset)
    end = data.find(b"\0", start)
    if end == -1:
        raise VerificationError("unterminated DEX string")
    return data[start:end].decode("utf-8", errors="replace")


def dex_class_descriptors(data: bytes) -> set[str]:
    if len(data) < 0x70 or not data.startswith(b"dex\n"):
        raise VerificationError("invalid DEX payload")

    string_ids_size = read_u32(data, 0x38)
    string_ids_off = read_u32(data, 0x3C)
    type_ids_size = read_u32(data, 0x40)
    type_ids_off = read_u32(data, 0x44)
    class_defs_size = read_u32(data, 0x60)
    class_defs_off = read_u32(data, 0x64)

    string_offsets = [
        read_u32(data, string_ids_off + index * 4)
        for index in range(string_ids_size)
    ]
    type_string_indexes = [
        read_u32(data, type_ids_off + index * 4)
        for index in range(type_ids_size)
    ]

    descriptors: set[str] = set()
    for index in range(class_defs_size):
        class_index = read_u32(data, class_defs_off + index * 32)
        if class_index >= len(type_string_indexes):
            raise VerificationError("invalid DEX class index")
        string_index = type_string_indexes[class_index]
        if string_index >= len(string_offsets):
            raise VerificationError("invalid DEX descriptor index")
        descriptors.add(read_dex_string(data, string_offsets[string_index]))
    return descriptors


def contains_axml_string(data: bytes, value: str) -> bool:
    return value.encode("utf-8") in data or value.encode("utf-16le") in data


def verify(apk_path: Path) -> None:
    if not apk_path.is_file():
        raise VerificationError(f"APK not found: {apk_path}")

    try:
        with zipfile.ZipFile(apk_path) as apk:
            names = set(apk.namelist())

            entry_path = "assets/xposed_init"
            if entry_path not in names:
                raise VerificationError("missing assets/xposed_init")
            try:
                entry_lines = apk.read(entry_path).decode("utf-8").splitlines()
            except UnicodeDecodeError as error:
                raise VerificationError("assets/xposed_init is not UTF-8") from error
            if entry_lines != [ENTRY_CLASS]:
                raise VerificationError(
                    f"assets/xposed_init must contain exactly: {ENTRY_CLASS}"
                )

            manifest_name = "AndroidManifest.xml"
            if manifest_name not in names:
                raise VerificationError("missing AndroidManifest.xml")
            manifest = apk.read(manifest_name)
            if contains_axml_string(manifest, "uses-permission"):
                raise VerificationError("AndroidManifest.xml declares a permission")
            for metadata_name in (
                "xposedmodule",
                "xposeddescription",
                "xposedminversion",
            ):
                if not contains_axml_string(manifest, metadata_name):
                    raise VerificationError(
                        f"AndroidManifest.xml is missing {metadata_name} metadata"
                    )

            dex_names = sorted(
                name
                for name in names
                if name.startswith("classes") and name.endswith(".dex")
            )
            if not dex_names:
                raise VerificationError("APK contains no classes*.dex")

            descriptors: set[str] = set()
            for dex_name in dex_names:
                descriptors.update(dex_class_descriptors(apk.read(dex_name)))

            if ENTRY_DESCRIPTOR not in descriptors:
                raise VerificationError("Xposed entry class is not packaged")
            leaked = sorted(
                descriptor
                for descriptor in descriptors
                if descriptor.startswith(XPOSED_DESCRIPTOR_PREFIX)
            )
            if leaked:
                raise VerificationError(
                    "compile-only Xposed stubs were packaged: " + ", ".join(leaked)
                )
    except zipfile.BadZipFile as error:
        raise VerificationError("file is not a valid APK/ZIP") from error


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("apk", type=Path, help="path to the APK to verify")
    args = parser.parse_args()

    try:
        verify(args.apk)
    except VerificationError as error:
        print(f"APK verification failed: {error}", file=sys.stderr)
        return 1

    print("APK verification passed")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
