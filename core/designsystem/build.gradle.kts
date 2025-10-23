plugins {
    alias(libs.plugins.biathlonk.android.library.compose)
    alias(libs.plugins.biathlonk.spotless)
}

android {
    namespace = "social.androiddev.hiberfake.biathlonk.core.designsystem"
}

dependencies {
    api(libs.androidx.compose.material3)
}
