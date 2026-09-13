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
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            // Signed with the local debug key so `assembleRelease` produces an
            // APK you can install straight away, with no keystore to manage.
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
