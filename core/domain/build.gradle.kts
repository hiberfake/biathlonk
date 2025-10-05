plugins {
    alias(libs.plugins.biathlonk.android.library)
    alias(libs.plugins.biathlonk.android.hilt)
    alias(libs.plugins.biathlonk.spotless)
}

android {
    namespace = "social.androiddev.hiberfake.biathlonk.core.domain"
}

dependencies {
    implementation(projects.core.common)
    api(projects.core.data)
}
