plugins {
    alias(libs.plugins.vibeplayer.android.application)
    alias(libs.plugins.kotzilla)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.fomaxtro.vibeplayer"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.designsystem)
    implementation(projects.core.ui)
    implementation(projects.core.player)

    implementation(projects.feature.onboarding)
    implementation(projects.feature.library)
    implementation(projects.feature.player)
    implementation(projects.feature.scanner)
    implementation(projects.feature.home)

    implementation(libs.kotzilla.sdk.compose)
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.bundles.navigation3)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.kotlinx.serialization.core)
}

kotzilla {
    composeInstrumentation = true
}