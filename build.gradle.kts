// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.vibeplayer.jvm.library) apply false
    alias(libs.plugins.vibeplayer.android.library) apply false
    alias(libs.plugins.vibeplayer.android.compose) apply false
    alias(libs.plugins.vibeplayer.feature.ui) apply false
    alias(libs.plugins.vibeplayer.android.application) apply false
    alias(libs.plugins.kotzilla) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false
}