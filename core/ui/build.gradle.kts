plugins {
    alias(libs.plugins.biathlonk.android.library.compose)
    alias(libs.plugins.biathlonk.spotless)
}

android {
    namespace = "social.androiddev.hiberfake.biathlonk.core.ui"
}

dependencies {
    implementation(projects.core.common)
    api(projects.core.designsystem)
    implementation(projects.core.model)

    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.ui.util)

    api(libs.coil.compose)
}
