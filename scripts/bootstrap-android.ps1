$ErrorActionPreference = 'Stop'

$projectRoot = Split-Path -Parent $PSScriptRoot
$toolsRoot = Join-Path $projectRoot '.tools'
$downloadsRoot = Join-Path $toolsRoot 'downloads'
$jdkExtractRoot = Join-Path $toolsRoot 'jdk-17.0.20+8'
$androidSdkRoot = Join-Path $toolsRoot 'android-sdk'

New-Item -ItemType Directory -Path $downloadsRoot -Force | Out-Null

function Get-Download {
    param(
        [Parameter(Mandatory = $true)][string]$Uri,
        [Parameter(Mandatory = $true)][string]$Destination,
        [string]$ExpectedSha256
    )

    $destinationPath = [IO.Path]::GetFullPath($Destination)
    $downloadsPath = [IO.Path]::GetFullPath($downloadsRoot) + [IO.Path]::DirectorySeparatorChar
    if (-not $destinationPath.StartsWith($downloadsPath, [StringComparison]::OrdinalIgnoreCase)) {
        throw "Download destination must stay inside $downloadsRoot"
    }

    if (Test-Path -LiteralPath $Destination) {
        if (-not $ExpectedSha256) {
            return
        }
        $actualSha256 = (Get-FileHash -LiteralPath $Destination -Algorithm SHA256).Hash
        if ($actualSha256.Equals($ExpectedSha256, [StringComparison]::OrdinalIgnoreCase)) {
            return
        }
        Remove-Item -LiteralPath $Destination -Force
    }

    $partialDestination = "$Destination.partial"
    if (Test-Path -LiteralPath $partialDestination) {
        Remove-Item -LiteralPath $partialDestination -Force
    }

    Write-Host "Downloading $Uri"
    Start-BitsTransfer -Source $Uri -Destination $partialDestination -DisplayName 'FR24 local Android build bootstrap'

    if ($ExpectedSha256) {
        $actualSha256 = (Get-FileHash -LiteralPath $partialDestination -Algorithm SHA256).Hash
        if (-not $actualSha256.Equals($ExpectedSha256, [StringComparison]::OrdinalIgnoreCase)) {
            throw "SHA-256 mismatch for $Uri"
        }
    }

    Move-Item -LiteralPath $partialDestination -Destination $Destination
}

$jdkArchive = Join-Path $downloadsRoot 'OpenJDK17U-jdk_x64_windows_hotspot_17.0.20_8.zip'
Get-Download `
    -Uri 'https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.20%2B8/OpenJDK17U-jdk_x64_windows_hotspot_17.0.20_8.zip' `
    -Destination $jdkArchive `
    -ExpectedSha256 '418497BE5CF585BDD2203D6486A565D66D3F5E992D5630D45104CB873FAB8122'

if (-not (Test-Path -LiteralPath $jdkExtractRoot)) {
    Expand-Archive -LiteralPath $jdkArchive -DestinationPath $jdkExtractRoot
}

$javaExecutable = Get-ChildItem -LiteralPath $jdkExtractRoot -Filter 'java.exe' -File -Recurse |
    Where-Object { $_.FullName -match '[\\/]bin[\\/]java\.exe$' } |
    Select-Object -First 1
if (-not $javaExecutable) {
    throw 'Portable JDK extraction did not produce bin\java.exe'
}
$env:JAVA_HOME = Split-Path -Parent (Split-Path -Parent $javaExecutable.FullName)
$env:Path = (Join-Path $env:JAVA_HOME 'bin') + [IO.Path]::PathSeparator + $env:Path

$commandLineArchive = Join-Path $downloadsRoot 'android-commandlinetools.zip'
Get-Download -Uri 'https://edgedl.me.gvt1.com/android/repository/commandlinetools-win-15859902_latest.zip' -Destination $commandLineArchive -ExpectedSha256 '90ae805d20434428bffcb699c290860f19bb5f66a67e6b330067e3de801fb04a'

$sdkManager = Join-Path $androidSdkRoot 'cmdline-tools\latest\bin\sdkmanager.bat'
if (-not (Test-Path -LiteralPath $sdkManager)) {
    $commandLineExtractRoot = Join-Path $toolsRoot 'android-commandlinetools-extracted'
    Expand-Archive -LiteralPath $commandLineArchive -DestinationPath $commandLineExtractRoot
    $latestRoot = Split-Path -Parent (Split-Path -Parent $sdkManager)
    New-Item -ItemType Directory -Path $latestRoot -Force | Out-Null
    Copy-Item -Path (Join-Path $commandLineExtractRoot 'cmdline-tools\*') -Destination $latestRoot -Recurse -Force
}

$env:ANDROID_HOME = $androidSdkRoot
$env:ANDROID_SDK_ROOT = $androidSdkRoot
$env:SDK_TEST_BASE_URL = 'https://edgedl.me.gvt1.com/android/repository/'

$licenseAnswers = 1..50 | ForEach-Object { 'y' }
$licenseAnswers | & $sdkManager --sdk_root=$androidSdkRoot --licenses | Out-Host
& $sdkManager --sdk_root=$androidSdkRoot 'platforms;android-35' 'build-tools;35.0.0' 'platform-tools'
if ($LASTEXITCODE -ne 0) {
    throw "sdkmanager failed with exit code $LASTEXITCODE"
}

$sdkPropertyPath = ($androidSdkRoot -replace '\\', '/')
[IO.File]::WriteAllText(
    (Join-Path $projectRoot 'local.properties'),
    "sdk.dir=$sdkPropertyPath`n",
    [Text.UTF8Encoding]::new($false)
)

Write-Host "JAVA_HOME=$env:JAVA_HOME"
Write-Host "ANDROID_SDK_ROOT=$env:ANDROID_SDK_ROOT"
Write-Host 'Android build environment is ready.'
