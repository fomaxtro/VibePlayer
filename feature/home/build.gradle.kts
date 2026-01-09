plugins {
    alias(libs.plugins.vibeplayer.feature.ui)
}

android {
    namespace = "com.fomaxtro.vibeplayer.feature.home"
}

dependencies {
    implementation(projects.feature.library)
    implementation(projects.feature.player)
    implementation(projects.feature.playlist)
}