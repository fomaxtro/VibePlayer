plugins {
    kotlin("plugin.compose")
}

dependencies {
    "implementation"(platform(libraries.findLibrary("androidx-compose-bom").get()))
    "implementation"(libraries.findLibrary("androidx-compose-ui").get())
    "implementation"(libraries.findLibrary("androidx-compose-ui-graphics").get())
    "implementation"(libraries.findLibrary("androidx-compose-ui-tooling-preview").get())
    "implementation"(libraries.findLibrary("androidx-compose-material3").get())
    "implementation"(libraries.findLibrary("androidx-lifecycle-runtime-ktx").get())
    "debugImplementation"(libraries.findLibrary("androidx-compose-ui-tooling").get())
}