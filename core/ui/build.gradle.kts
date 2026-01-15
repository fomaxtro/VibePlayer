plugins {
    alias(libs.plugins.vibeplayer.android.library)
    alias(libs.plugins.vibeplayer.android.compose)
}

android {
    namespace = "com.fomaxtro.vibeplayer.core.ui"
}

dependencies {
    implementation(projects.core.domain)
    implementation(libs.androidx.navigation3.runtime)
}