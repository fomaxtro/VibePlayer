import org.jetbrains.kotlin.gradle.dsl.KotlinJvmExtension

plugins {
    id("java-library")
    kotlin("jvm")
}

configure<KotlinJvmExtension> {
    jvmToolchain(11)
}