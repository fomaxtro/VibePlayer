import com.android.build.api.dsl.LibraryExtension

plugins {
    id("com.android.library")
}

configure<JavaPluginExtension> {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

extensions.configure<LibraryExtension> {
    compileSdk {
        version = release(libraries.findVersion("appCompileSdk").get().toString().toInt())
    }

    defaultConfig {
        minSdk = libraries.findVersion("appMinSdk").get().toString().toInt()

        consumerProguardFile("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    "implementation"(libraries.findLibrary("androidx-core-ktx").get())
    "implementation"(libraries.findLibrary("koin-android").get())
    "implementation"(libraries.findLibrary("timber").get())
}