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
    private static final ResourceHookDiagnostics RESOURCE_HOOK_DIAGNOSTICS =
            new ResourceHookDiagnostics(64);
    private static final ResourceDiagnosticSink RESOURCE_DIAGNOSTIC_SINK =
            new ResourceDiagnosticSink(new ResourceDiagnosticSink.Writer() {
                @Override
                public void write(String record) {
                    XposedBridge.log(record);
                }
            });
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
            logResourceDiagnostic(RESOURCE_HOOK_DIAGNOSTICS.installation(
                    methodName,
                    hooks == null ? 0 : hooks.size()));
        } catch (Throwable error) {
            logResourceDiagnostic(RESOURCE_HOOK_DIAGNOSTICS.callback(
                    methodName,
                    null,
                    null,
                    null,
                    false,
                    false,
                    "install_failed"));
            logInstallFailure("resource hook " + methodName, error);
        }
    }

    private static XC_MethodHook createResourceCallback(final String methodName) {
        return new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                Integer resourceId = HookArguments.resourceId(param.args);
                Object original = null;
                try {
                    original = param.getResult();
                    if (resourceId == null) {
                        logResourceDiagnostic(RESOURCE_HOOK_DIAGNOSTICS.callback(
                                methodName,
                                null,
                                null,
                                original,
                                false,
                                false,
                                "invalid_resource_id"));
                        return;
                    }
                    if (!(param.thisObject instanceof Resources)) {
                        logResourceDiagnostic(RESOURCE_HOOK_DIAGNOSTICS.callback(
                                methodName,
                                resourceId,
                                null,
                                original,
                                false,
                                false,
                                "not_resources"));
                        return;
                    }
                    String resourceName;
                    try {
                        resourceName = ((Resources) param.thisObject)
                                .getResourceEntryName(resourceId);
                    } catch (Throwable ignored) {
                        logResourceDiagnostic(RESOURCE_HOOK_DIAGNOSTICS.callback(
                                methodName,
                                resourceId,
                                null,
                                original,
                                false,
                                false,
                                "entry_name_unresolved"));
                        return;
                    }
                    boolean stringArrayMethod = "getStringArray".equals(methodName);
                    if (stringArrayMethod
                            ? !(original instanceof String[])
                            : !(original instanceof String)) {
                        logResourceDiagnostic(RESOURCE_HOOK_DIAGNOSTICS.callback(
                                methodName,
                                resourceId,
                                resourceName,
                                original,
                                false,
                                false,
                                stringArrayMethod ? "result_not_string_array" : "result_not_string"));
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
                    } else if ("selected".equals(resourceName)) {
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
                    boolean dictionaryHit = !original.equals(translated);
                    if (!dictionaryHit) {
                        logResourceDiagnostic(RESOURCE_HOOK_DIAGNOSTICS.callback(
                                methodName,
                                resourceId,
                                resourceName,
                                original,
                                false,
                                false,
                                "dictionary_miss"));
                        return;
                    }
                    param.setResult(translated);
                    logResourceDiagnostic(RESOURCE_HOOK_DIAGNOSTICS.callback(
                            methodName,
                            resourceId,
                            resourceName,
                            original,
                            true,
                            true,
                            "applied"));
                } catch (Throwable ignored) {
                    logResourceDiagnostic(RESOURCE_HOOK_DIAGNOSTICS.callback(
                            methodName,
                            resourceId,
                            null,
                            original,
                            false,
                            false,
                            "callback_error"));
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
        String diagnosticMethod = "typedArray." + methodName;
        try {
            Set<XC_MethodHook.Unhook> hooks = XposedBridge.hookAllMethods(
                    TypedArray.class,
                    methodName,
                    createTypedArrayCallback(methodName, diagnosticMethod));
            logResourceDiagnostic(RESOURCE_HOOK_DIAGNOSTICS.installation(
                    diagnosticMethod,
                    hooks == null ? 0 : hooks.size()));
        } catch (Throwable error) {
            logResourceDiagnostic(RESOURCE_HOOK_DIAGNOSTICS.callback(
                    diagnosticMethod,
                    null,
                    null,
                    null,
                    false,
                    false,
                    "install_failed"));
            logInstallFailure("TypedArray hook " + methodName, error);
        }
    }

    private static XC_MethodHook createTypedArrayCallback(
            final String methodName,
            final String diagnosticMethod) {
        return new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                Integer index = HookArguments.typedArrayIndex(param.args);
                Integer resourceId = null;
                Object original = null;
                try {
                    original = param.getResult();
                    if (index == null) {
                        logResourceDiagnostic(RESOURCE_HOOK_DIAGNOSTICS.callback(
                                diagnosticMethod,
                                null,
                                null,
                                original,
                                false,
                                false,
                                "invalid_index"));
                        return;
                    }
                    if (!(param.thisObject instanceof TypedArray)) {
                        logResourceDiagnostic(RESOURCE_HOOK_DIAGNOSTICS.callback(
                                diagnosticMethod,
                                null,
                                null,
                                original,
                                false,
                                false,
                                "not_typed_array"));
                        return;
                    }
                    TypedArray typedArray = (TypedArray) param.thisObject;
                    int resolvedResourceId = typedArray.getResourceId(index, 0);
                    if (resolvedResourceId == 0) {
                        logResourceDiagnostic(RESOURCE_HOOK_DIAGNOSTICS.callback(
                                diagnosticMethod,
                                null,
                                null,
                                original,
                                false,
                                false,
                                "inline_literal"));
                        return;
                    }
                    resourceId = resolvedResourceId;
                    Resources resources = typedArray.getResources();
                    if (resources == null) {
                        logResourceDiagnostic(RESOURCE_HOOK_DIAGNOSTICS.callback(
                                diagnosticMethod,
                                resourceId,
                                null,
                                original,
                                false,
                                false,
                                "resources_unavailable"));
                        return;
                    }
                    String resourceName;
                    try {
                        resourceName = resources.getResourceEntryName(resourceId);
                    } catch (Throwable ignored) {
                        logResourceDiagnostic(RESOURCE_HOOK_DIAGNOSTICS.callback(
                                diagnosticMethod,
                                resourceId,
                                null,
                                original,
                                false,
                                false,
                                "entry_name_unresolved"));
                        return;
                    }
                    boolean textArrayMethod = "getTextArray".equals(methodName);
                    if (textArrayMethod
                            ? !(original instanceof CharSequence[])
                            : !(original instanceof String)) {
                        logResourceDiagnostic(RESOURCE_HOOK_DIAGNOSTICS.callback(
                                diagnosticMethod,
                                resourceId,
                                resourceName,
                                original,
                                false,
                                false,
                                textArrayMethod ? "result_not_text_array" : "result_not_string"));
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
                    if (original.equals(translated)) {
                        logResourceDiagnostic(RESOURCE_HOOK_DIAGNOSTICS.callback(
                                diagnosticMethod,
                                resourceId,
                                resourceName,
                                original,
                                false,
                                false,
                                "dictionary_miss"));
                        return;
                    }
                    param.setResult(translated);
                    logResourceDiagnostic(RESOURCE_HOOK_DIAGNOSTICS.callback(
                            diagnosticMethod,
                            resourceId,
                            resourceName,
                            original,
                            true,
                            true,
                            "applied"));
                } catch (Throwable ignored) {
                    logResourceDiagnostic(RESOURCE_HOOK_DIAGNOSTICS.callback(
                            diagnosticMethod,
                            resourceId,
                            null,
                            original,
                            false,
                            false,
                            "callback_error"));
                }
            }
        };
    }

    private static void logResourceDiagnostic(String message) {
        if (message == null) {
            return;
        }
        try {
            RESOURCE_DIAGNOSTIC_SINK.emit(message);
        } catch (Throwable ignored) {
            // Logging must never alter FR24's resource result.
        }
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
