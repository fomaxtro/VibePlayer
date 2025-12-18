plugins {
    alias(libs.plugins.vibeplayer.android.library)
    alias(libs.plugins.vibeplayer.android.compose)
}

android {
    namespace = "com.fomaxtro.vibeplayer.core.designsystem"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    api(libs.androidx.compose.material3.adaptive)
    implementation(libs.bundles.coil)
}