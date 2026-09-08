package de.robv.android.xposed;

public abstract class XC_MethodHook {
    protected XC_MethodHook() {
    }

    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
    }

    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
    }

    public static final class MethodHookParam {
        public Object thisObject;
        public Object[] args;

        public Object getResult() {
            throw new UnsupportedOperationException("Compile-only Xposed API stub");
        }

        public void setResult(Object result) {
            throw new UnsupportedOperationException("Compile-only Xposed API stub");
        }
    }

    public final class Unhook {
        private Unhook() {
        }
    }
}
