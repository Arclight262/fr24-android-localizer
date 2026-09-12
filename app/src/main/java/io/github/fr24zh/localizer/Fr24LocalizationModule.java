package io.github.fr24zh.localizer;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public final class Fr24LocalizationModule implements IXposedHookLoadPackage {
    private static final String LOG_PREFIX = "FR24ZH: ";
    private static final AccessibilityNodeHookArguments.Translator
            MAP_ACCESSIBILITY_TRANSLATOR =
            new AccessibilityNodeHookArguments.Translator() {
                @Override
                public Object translate(Object value) {
                    return MapAccessibilityTranslation.translate(value);
                }
            };
    private static final AtomicBoolean INSTALLED = new AtomicBoolean(false);

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        if (!TargetPolicy.shouldLoad(
                loadPackageParam.packageName,
                loadPackageParam.processName)) {
            return;
        }
        if (!INSTALLED.compareAndSet(false, true)) {
            return;
        }

        installResourceHooks();
        installTypedArrayHooks();
        installTextHook(createTextArgumentCallback(false));
        installContentDescriptionHook(createTextArgumentCallback(true));
        installAccessibilityNodeDescriptionHook();
    }

    private static XC_MethodHook createTextArgumentCallback(
            final boolean contentDescription) {
        return new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) {
                try {
                    if (param.args == null || param.args.length == 0) {
                        return;
                    }
                    Object original = param.args[0];
                    Object translated = contentDescription
                            ? HookTranslation.translateContentDescriptionArgument(original)
                            : HookTranslation.translateTextArgument(
                                    textViewResourceEntryName(param.thisObject),
                                    original);
                    if (original != translated) {
                        param.args[0] = translated;
                    }
                } catch (Throwable ignored) {
                    // Keep the original argument.
                }
            }
        };
    }

    private static String textViewResourceEntryName(Object candidate) {
        if (!(candidate instanceof TextView)) {
            return null;
        }
        try {
            TextView textView = (TextView) candidate;
            int viewId = textView.getId();
            if (viewId == View.NO_ID) {
                return null;
            }
            return textView.getResources().getResourceEntryName(viewId);
        } catch (Throwable ignored) {
            return null;
        }
    }

    private static void installResourceHooks() {
        installResourceHook("getText");
        installResourceHook("getString");
        installResourceHook("getStringArray");
        installResourceHook("getQuantityString");
    }

    private static void installResourceHook(final String methodName) {
        try {
            Set<XC_MethodHook.Unhook> hooks = XposedBridge.hookAllMethods(
                    Resources.class,
                    methodName,
                    createResourceCallback(methodName));
            XposedBridge.log(LOG_PREFIX + "resource hook installed method="
                    + methodName + " hookCount=" + (hooks == null ? 0 : hooks.size()));
        } catch (Throwable error) {
            logInstallFailure("resource hook " + methodName, error);
        }
    }

    private static XC_MethodHook createResourceCallback(final String methodName) {
        return new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                Integer resourceId = HookArguments.resourceId(param.args);
                try {
                    if (resourceId == null) {
                        return;
                    }
                    if (!(param.thisObject instanceof Resources)) {
                        return;
                    }
                    String resourceName;
                    try {
                        resourceName = ((Resources) param.thisObject)
                                .getResourceEntryName(resourceId);
                    } catch (Throwable ignored) {
                        return;
                    }
                    Object original = param.getResult();
                    boolean stringArrayMethod = "getStringArray".equals(methodName);
                    if (stringArrayMethod
                            ? !(original instanceof String[])
                            : !(original instanceof String)) {
                        return;
                    }
                    Object translated;
                    if (stringArrayMethod) {
                        translated = HookTranslation.translateTextArrayResult(
                                resourceName,
                                original,
                                param.args);
                    } else if ("getQuantityString".equals(methodName)) {
                        translated = HookTranslation.translateQuantityResourceResult(
                                resourceName,
                                original,
                                param.args);
                    } else if (HookTranslation.needsSelectedContextStack(
                            resourceName, original, param.args)) {
                        translated = HookTranslation.translateResourceCall(
                                resourceName,
                                original,
                                param.args,
                                Thread.currentThread().getStackTrace());
                    } else {
                        translated = HookTranslation.translateResourceCall(
                                resourceName,
                                original,
                                param.args);
                    }
                    if (!original.equals(translated)) {
                        param.setResult(translated);
                    }
                } catch (Throwable ignored) {
                    // Keep the original result.
                }
            }
        };
    }

    private static void installTypedArrayHooks() {
        installTypedArrayHook("getText");
        installTypedArrayHook("getString");
        installTypedArrayHook("getTextArray");
    }

    private static void installTypedArrayHook(final String methodName) {
        try {
            Set<XC_MethodHook.Unhook> hooks = XposedBridge.hookAllMethods(
                    TypedArray.class,
                    methodName,
                    createTypedArrayCallback(methodName));
            XposedBridge.log(LOG_PREFIX + "TypedArray hook installed method="
                    + methodName + " hookCount=" + (hooks == null ? 0 : hooks.size()));
        } catch (Throwable error) {
            logInstallFailure("TypedArray hook " + methodName, error);
        }
    }

    private static XC_MethodHook createTypedArrayCallback(final String methodName) {
        return new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                Integer index = HookArguments.typedArrayIndex(param.args);
                try {
                    if (index == null) {
                        return;
                    }
                    if (!(param.thisObject instanceof TypedArray)) {
                        return;
                    }
                    TypedArray typedArray = (TypedArray) param.thisObject;
                    int resolvedResourceId = typedArray.getResourceId(index, 0);
                    if (resolvedResourceId == 0) {
                        return;
                    }
                    Resources resources = typedArray.getResources();
                    if (resources == null) {
                        return;
                    }
                    String resourceName;
                    try {
                        resourceName = resources.getResourceEntryName(resolvedResourceId);
                    } catch (Throwable ignored) {
                        return;
                    }
                    Object original = param.getResult();
                    boolean textArrayMethod = "getTextArray".equals(methodName);
                    if (textArrayMethod
                            ? !(original instanceof CharSequence[])
                            : !(original instanceof String)) {
                        return;
                    }
                    Object translated = textArrayMethod
                            ? HookTranslation.translateTextArrayResult(
                                    resourceName,
                                    original,
                                    param.args)
                            : HookTranslation.translateRawResourceResult(
                                    resourceName,
                                    original);
                    if (!original.equals(translated)) {
                        param.setResult(translated);
                    }
                } catch (Throwable ignored) {
                    // Keep the original result.
                }
            }
        };
    }

    private static void installTextHook(XC_MethodHook callback) {
        try {
            XposedBridge.hookAllMethods(TextView.class, "setText", callback);
            XposedBridge.log(LOG_PREFIX + "text hook installed");
        } catch (Throwable error) {
            logInstallFailure("text hook", error);
        }
    }

    private static void installContentDescriptionHook(XC_MethodHook callback) {
        try {
            XposedBridge.hookAllMethods(View.class, "setContentDescription", callback);
            XposedBridge.log(LOG_PREFIX + "content-description hook installed");
        } catch (Throwable error) {
            logInstallFailure("content-description hook", error);
        }
    }

    private static void installAccessibilityNodeDescriptionHook() {
        try {
            Set<XC_MethodHook.Unhook> hooks = XposedBridge.hookAllMethods(
                    AccessibilityNodeInfo.class,
                    "setContentDescription",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            AccessibilityNodeHookArguments.translateDescription(
                                    param.args,
                                    MAP_ACCESSIBILITY_TRANSLATOR);
                        }
                    });
            XposedBridge.log(LOG_PREFIX
                    + "accessibility-node content-description hook installed hookCount="
                    + (hooks == null ? 0 : hooks.size()));
        } catch (Throwable ignored) {
            XposedBridge.log(LOG_PREFIX
                    + "accessibility-node content-description hook install_failed hookCount=0");
        }
    }

    private static void logInstallFailure(String stage, Throwable error) {
        XposedBridge.log(LOG_PREFIX + "failed to install " + stage);
        XposedBridge.log(error);
    }
}
