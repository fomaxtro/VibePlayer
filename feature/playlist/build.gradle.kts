plugins {
    alias(libs.plugins.vibeplayer.feature.ui)
}

android {
    namespace = "com.fomaxtro.vibeplayer.feature.playlist"
}

dependencies {
    implementation(libs.bundles.coil)
}