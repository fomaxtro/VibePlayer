import extension.libraries
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmExtension

plugins {
    id("java-library")
    kotlin("jvm")
}

configure<KotlinJvmExtension> {
    jvmToolchain(21)
}

dependencies {
    "implementation"(libraries.findLibrary("koin-core").get())
}