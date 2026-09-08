package io.github.fr24zh.localizer;

final class TargetPolicy {
    static final String TARGET_PACKAGE = "com.flightradar24free";

    private TargetPolicy() {
    }

    static boolean shouldLoad(String packageName, String processName) {
        return TARGET_PACKAGE.equals(packageName)
                && processName != null
                && (TARGET_PACKAGE.equals(processName)
                || processName.startsWith(TARGET_PACKAGE + ":"));
    }
}
