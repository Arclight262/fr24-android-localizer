# FR24 Android Localizer Repository Maintenance Design

## Objective

Improve first-use clarity, reduce runtime overhead, strengthen translation regression coverage, and simplify repository maintenance without weakening compatibility with Legacy Xposed API 82 implementations or changing the target Flightradar24 package.

## Constraints

- Keep `com.flightradar24free` as the only target package.
- Keep Android 8.1/API 27 as the minimum supported Android version.
- Use only the standard Legacy Xposed API 82 surface already used by the module.
- Preserve fail-open behavior: unknown resources, unexpected types, formatting failures, and hook errors leave the original value unchanged.
- Do not add Android permissions, a launcher activity, network access, analytics, settings UI, runtime language files, or private framework APIs.
- Do not commit a signing key or claim stable upgrade support until a persistent release key is configured outside the repository.
- Do not narrow hook installation to the main process or rewrite styled `Spanned` content without later device evidence.

## Chosen Approach

Use a conservative staged cleanup. Remove work that is provably redundant or runs on every resource callback, improve tests around existing behavior, and make delivery metadata clearer. Defer compatibility-sensitive behavior changes that cannot be validated by JVM tests alone.

The rejected alternatives are:

- A broad rewrite into runtime JSON or generated localization assets. This adds parsing and build complexity while the current APK is already small.
- An aggressive hook-scope reduction to the main process. This may silently remove translations from an unverified FR24 child process.
- Blind conversion of every `CharSequence` or `Spanned` value to `String`. This can remove clickable spans and styling.

## Runtime Changes

Remove per-resource callback diagnostics from production execution. Keep concise installation success and installation failure logging so users can still determine whether the module loaded. This removes synchronized bookkeeping, repeated resource-miss records, and two diagnostic-only production classes.

Retain the context-sensitive `selected` translation, but collect the current stack only after the resource name, original value, and call arguments match its known shape. All unrelated uses of the `selected` resource avoid stack allocation.

Keep package and process policy unchanged in this maintenance pass. Keep styled text fail-open unchanged and document it as a compatibility boundary pending a real FR24 sample.

## Translation and Test Changes

Move the duplicated `Flight: <identifier>` translation into one method owned by `DynamicLabelTranslation`. The map accessibility hook will call that method directly, allowing the redundant map-specific translator and duplicate tests to be removed without broadening matching rules.

Remove production methods used only by tests when their asserted invariant is already enforced during dictionary construction. Consolidate repeated dictionary-size assertions into one helper and one explicit baseline test.

Add table-driven tests for every currently uncovered formatted resource at the start of the resource dictionary. Tests must verify valid formatting and fail-open behavior for incompatible arguments. No runtime format-signature subsystem will be added in this pass because `Resources.getString(id, args)` does not expose the original unformatted template to the after-hook without recursive resource access.

## Delivery and Documentation Changes

Update all five README files so the installation sequence includes installing the APK before opening the Xposed manager, and use the exact application label `FR24 中文化（非官方测试）`. Point ordinary users to GitHub Releases first and identify GitHub Actions artifacts as temporary test outputs.

The GitHub Actions build will run unit tests, Android Lint, assemble the debug APK, verify packaging, generate a SHA-256 file, and upload the APK together with build metadata containing version and commit identity. Debug signing remains explicitly documented.

Pin the Windows bootstrap JDK download to one verified Temurin 17 archive and SHA-256. Since the Gradle Wrapper is tracked, remove the separate Gradle distribution download and wrapper-generation fallback.

The application intentionally has no launcher activity and stores no user data. Resolve the two intentional Android Lint warnings with narrowly scoped manifest annotations and explanatory comments rather than adding unused icons or backup resources.

## Verification

- Each runtime behavior change begins with a focused failing unit test.
- Run the complete JVM unit-test suite.
- Run `:app:lintDebug` and require zero errors and zero unexplained warnings.
- Build a fresh debug APK.
- Verify Xposed entry metadata, absence of Android permissions, absence of packaged Xposed stubs, APK signature validity, and SHA-256 output.
- Confirm the Git worktree contains only intended source, test, documentation, and workflow changes.

## Deferred Work

- Restricting hooks to the main process requires an ADB process inventory while representative FR24 screens are open.
- Translating styled `Spanned` resources requires a captured example and a span-preservation policy.
- Stable in-place upgrades require a persistent release signing key configured by the repository owner.
- Performance claims require device profiling before and after the runtime cleanup.
