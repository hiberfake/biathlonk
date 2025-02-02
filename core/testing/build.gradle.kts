plugins {
    alias(libs.plugins.biathlonk.android.library.compose)
    alias(libs.plugins.biathlonk.spotless)
}

android {
    namespace = "social.androiddev.hiberfake.biathlonk.core.testing"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.data)

    api(libs.androidx.test.core)
    api(libs.androidx.test.espresso)
    api(libs.androidx.test.junit)
    api(libs.androidx.test.runner)
    api(libs.androidx.test.rules)

    api(libs.androidx.compose.ui.testJunit4)
    debugApi(libs.androidx.compose.ui.testManifest)

    api(libs.junit)

    api(libs.mockk)
}
