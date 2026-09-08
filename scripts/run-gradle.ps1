param(
    [Parameter(ValueFromRemainingArguments = $true)]
    [string[]]$GradleArguments
)

$ErrorActionPreference = 'Stop'
$projectRoot = Split-Path -Parent $PSScriptRoot
$jdkRoot = Join-Path $projectRoot '.tools\jdk-17'
$javaExecutable = Get-ChildItem -LiteralPath $jdkRoot -Filter 'java.exe' -File -Recurse |
    Where-Object { $_.FullName -match '[\\/]bin[\\/]java\.exe$' } |
    Select-Object -First 1

if (-not $javaExecutable) {
    throw 'Run scripts/bootstrap-android.ps1 before Gradle.'
}

$env:JAVA_HOME = Split-Path -Parent (Split-Path -Parent $javaExecutable.FullName)
$env:Path = (Join-Path $env:JAVA_HOME 'bin') + [IO.Path]::PathSeparator + $env:Path
$env:ANDROID_HOME = Join-Path $projectRoot '.tools\android-sdk'
$env:ANDROID_SDK_ROOT = $env:ANDROID_HOME
$env:SDK_TEST_BASE_URL = 'https://edgedl.me.gvt1.com/android/repository/'

& (Join-Path $projectRoot 'gradlew.bat') @GradleArguments
exit $LASTEXITCODE
