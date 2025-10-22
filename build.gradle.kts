plugins {
    // Android
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.test) apply false

    // AndroidX
    alias(libs.plugins.androidx.baselineprofile) apply false

    // Kotlin
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.parcelize) apply false

    // Google
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false

    // Third-party
    alias(libs.plugins.aboutlibraries) apply false
    alias(libs.plugins.aboutlibraries.android) apply false

    alias(libs.plugins.spotless)

    alias(libs.plugins.moduleGraph)
}
