plugins {
    alias(libs.plugins.vibeplayer.android.library)
}

android {
    namespace = "com.fomaxtro.vibeplayer.core.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.database)
}