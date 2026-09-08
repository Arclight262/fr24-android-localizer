package io.github.fr24zh.localizer;

final class HookArguments {
    private HookArguments() {
    }

    static Integer resourceId(Object[] arguments) {
        if (arguments == null || arguments.length == 0 || !(arguments[0] instanceof Integer)) {
            return null;
        }
        return (Integer) arguments[0];
    }

    static Object[] formatArguments(Object[] arguments) {
        if (arguments == null || arguments.length < 2 || !(arguments[1] instanceof Object[])) {
            return null;
        }
        return (Object[]) arguments[1];
    }

    static boolean hasFormatArguments(Object[] arguments) {
        return arguments != null
                && arguments.length >= 2
                && arguments[1] instanceof Object[];
    }

    static Integer quantityStringQuantity(Object[] arguments) {
        if (!hasQuantityStringFormatArguments(arguments)) {
            return null;
        }
        return (Integer) arguments[1];
    }

    static Integer quantityStringQuantityWithoutFormatArguments(Object[] arguments) {
        if (arguments == null
                || arguments.length != 2
                || !(arguments[0] instanceof Integer)
                || !(arguments[1] instanceof Integer)
                || ((Integer) arguments[1]) < 0) {
            return null;
        }
        return (Integer) arguments[1];
    }

    static Object[] quantityStringFormatArguments(Object[] arguments) {
        if (!hasQuantityStringFormatArguments(arguments)) {
            return null;
        }
        return (Object[]) arguments[2];
    }

    private static boolean hasQuantityStringFormatArguments(Object[] arguments) {
        return arguments != null
                && arguments.length == 3
                && arguments[0] instanceof Integer
                && arguments[1] instanceof Integer
                && ((Integer) arguments[1]) >= 0
                && arguments[2] instanceof Object[];
    }

    static Integer typedArrayIndex(Object[] arguments) {
        if (arguments == null || arguments.length == 0 || !(arguments[0] instanceof Integer)) {
            return null;
        }
        Integer index = (Integer) arguments[0];
        return index >= 0 ? index : null;
    }
}
