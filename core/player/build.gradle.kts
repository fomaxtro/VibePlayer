plugins {
    alias(libs.plugins.vibeplayer.android.library)
}

android {
    namespace = "com.fomaxtro.vibeplayer.core.player"
}

dependencies {
    implementation(projects.core.domain)
    implementation(libs.androidx.media3.exoplayer)
}