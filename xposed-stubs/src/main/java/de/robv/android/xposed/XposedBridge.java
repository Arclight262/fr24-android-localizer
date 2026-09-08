package de.robv.android.xposed;

import java.util.Set;

public final class XposedBridge {
    private XposedBridge() {
    }

    public static Set<XC_MethodHook.Unhook> hookAllMethods(
            Class<?> hookClass,
            String methodName,
            XC_MethodHook callback) {
        throw new UnsupportedOperationException("Compile-only Xposed API stub");
    }

    public static void log(String message) {
        throw new UnsupportedOperationException("Compile-only Xposed API stub");
    }

    public static void log(Throwable error) {
        throw new UnsupportedOperationException("Compile-only Xposed API stub");
    }
}
