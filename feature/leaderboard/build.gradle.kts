plugins {
    alias(libs.plugins.biathlonk.android.feature)
    alias(libs.plugins.biathlonk.android.hilt)
    alias(libs.plugins.biathlonk.spotless)
}

android {
    namespace = "social.androiddev.hiberfake.biathlonk.leaderboard"
}
