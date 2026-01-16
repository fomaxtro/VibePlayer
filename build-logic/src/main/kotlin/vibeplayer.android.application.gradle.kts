import com.android.build.api.dsl.ApplicationExtension

plugins {
    id("com.android.application")
    id("vibeplayer.android.compose")
}

configure<JavaPluginExtension> {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configure<ApplicationExtension> {
    compileSdk {
        version = release(libraries.findVersion("appCompileSdk").get().toString().toInt())
    }

    defaultConfig {
        applicationId = libraries.findVersion("appApplicationId").get().toString()
        minSdk = libraries.findVersion("appMinSdk").get().toString().toInt()
        targetSdk = libraries.findVersion("appTargetSdk").get().toString().toInt()
        versionName = libraries.findVersion("appVersionName").get().toString()
        versionCode = libraries.findVersion("appVersionCode").get().toString().toInt()
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    "implementation"(libraries.findLibrary("androidx-core-ktx").get())
    "implementation"(libraries.findLibrary("koin-android").get())
    "implementation"(libraries.findLibrary("koin-androidx-compose").get())
    "implementation"(libraries.findLibrary("timber").get())
}