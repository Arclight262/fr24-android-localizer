pluginManagement {
    repositories {
        maven("https://edgedl.me.gvt1.com/dl/android/maven2/")
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven("https://edgedl.me.gvt1.com/dl/android/maven2/")
        mavenCentral()
    }
}

rootProject.name = "fr24-android-localizer"
include(":app")
include(":xposed-stubs")
