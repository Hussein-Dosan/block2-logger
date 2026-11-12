plugins {
    id("com.android.application")
}

android {
    namespace = "com.hussein.blocklogger"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hussein.blocklogger"
        minSdk = 26
        targetSdk = 34
        versionCode = 3
        versionName = "4.0"
    }

    signingConfigs {
        // A fixed key committed to the repo, so every build - here, in CI, on
        // any machine - produces an APK Android accepts as an update to the
        // last one. The auto-generated debug key differs on every CI runner,
        // which is what caused "App not installed".
        create("stable") {
            storeFile = rootProject.file("debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("stable")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
