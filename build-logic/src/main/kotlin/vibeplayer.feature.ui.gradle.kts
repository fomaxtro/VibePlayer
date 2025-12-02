import com.android.build.api.dsl.LibraryExtension
import org.gradle.kotlin.dsl.configure

plugins {
    id("vibeplayer.android.library")
    id("vibeplayer.android.compose")
}

configure<LibraryExtension> {
    buildFeatures {
        compose = true
    }
}

dependencies {
    "implementation"(libraries.findLibrary("androidx-activity-compose").get())
    "implementation"(libraries.findLibrary("koin-androidx-compose").get())
}