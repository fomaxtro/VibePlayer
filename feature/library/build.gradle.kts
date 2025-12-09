plugins {
    alias(libs.plugins.vibeplayer.feature.ui)
}


android {
    namespace = "com.fomaxtro.vibeplayer.feature.library"
}

dependencies {
    implementation(libs.bundles.coil)
}