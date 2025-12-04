import com.android.build.api.dsl.LibraryExtension
import org.gradle.kotlin.dsl.configure

plugins {
    id("vibeplayer.android.library")
    id("vibeplayer.android.compose")
    kotlin("plugin.serialization")
}

configure<LibraryExtension> {
    buildFeatures {
        compose = true
    }
}

dependencies {
    "implementation"(project(":core:designsystem"))
    "implementation"(project(":core:ui"))
    "implementation"(libraries.findLibrary("androidx-activity-compose").get())
    "implementation"(libraries.findLibrary("koin-androidx-compose").get())
    "implementation"(libraries.findLibrary("kotlinx-serialization-core").get())
    "implementation"(libraries.findLibrary("androidx-navigation3-runtime").get())
}