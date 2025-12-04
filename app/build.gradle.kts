plugins {
    alias(libs.plugins.vibeplayer.android.application)
    alias(libs.plugins.kotzilla)
}

android {
    namespace = "com.fomaxtro.vibeplayer"
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.feature.scanner)

    implementation(libs.kotzilla.sdk.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
}

kotzilla {
    composeInstrumentation = true
}