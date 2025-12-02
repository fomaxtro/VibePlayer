import com.android.build.api.dsl.LibraryExtension
import extension.libraries

plugins {
    id("vibeplayer.android.library")
    kotlin("plugin.compose")
}

configure<LibraryExtension> {
    buildFeatures {
        compose = true
    }
}

dependencies {
    "implementation"(platform(libraries.findLibrary("androidx-compose-bom").get()))
    "implementation"(libraries.findLibrary("androidx-compose-ui").get())
    "implementation"(libraries.findLibrary("androidx-compose-ui-graphics").get())
    "implementation"(libraries.findLibrary("androidx-compose-ui-tooling-preview").get())
    "debugImplementation"(libraries.findLibrary("androidx-compose-ui-tooling").get())
}