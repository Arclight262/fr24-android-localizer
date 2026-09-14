import org.gradle.api.tasks.compile.JavaCompile

val moduleVersionCode: String by project
val moduleVersionName: String by project

plugins {
    id("com.android.application")
}

android {
    namespace = "io.github.fr24zh.localizer"
    compileSdk = 35
    buildToolsVersion = "35.0.0"

    defaultConfig {
        applicationId = "io.github.fr24zh.localizer"
        minSdk = 27
        targetSdk = 35
        versionCode = moduleVersionCode.toInt()
        versionName = moduleVersionName
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    compileOnly(project(":xposed-stubs"))
    testImplementation("junit:junit:4.13.2")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}
