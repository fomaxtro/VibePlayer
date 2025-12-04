plugins {
    alias(libs.plugins.vibeplayer.android.application)
    alias(libs.plugins.kotzilla)
}

android {
    namespace = "com.fomaxtro.vibeplayer"
}

dependencies {
    implementation(libs.kotzilla.sdk.compose)
    implementation(projects.core.designsystem)
    implementation(libs.androidx.core.splashscreen)
}

kotzilla {
    composeInstrumentation = true
}