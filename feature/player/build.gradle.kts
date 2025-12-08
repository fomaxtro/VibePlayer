plugins {
    alias(libs.plugins.vibeplayer.feature.ui)
}

android {
    namespace = "com.fomaxtro.vibeplayer.feature.player"
}

dependencies {
    implementation(libs.bundles.coil)
}