plugins {
    alias(libs.plugins.vibeplayer.android.application)
    alias(libs.plugins.kotzilla)
}

android {
    namespace = "com.fomaxtro.vibeplayer"
}

dependencies {
    implementation(libs.kotzilla.sdk.compose)
}

kotzilla {
    composeInstrumentation = true
}