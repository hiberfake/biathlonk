plugins {
    alias(libs.plugins.biathlonk.android.library)
    alias(libs.plugins.biathlonk.android.hilt)
    alias(libs.plugins.biathlonk.spotless)
}

android {
    namespace = "social.androiddev.hiberfake.biathlonk.core.data"
}

dependencies {
    implementation(projects.core.common)
    api(projects.core.model)
    implementation(projects.core.network)

    api(libs.sandwich)
}
