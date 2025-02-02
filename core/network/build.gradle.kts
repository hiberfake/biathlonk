plugins {
    alias(libs.plugins.biathlonk.android.library)
    alias(libs.plugins.biathlonk.android.hilt)
    alias(libs.plugins.biathlonk.spotless)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "social.androiddev.hiberfake.biathlonk.core.network"

    defaultConfig {
        buildConfigField(
            type = "String",
            name = "BASE_URL",
            value = "\"https://biathlonresults.com/modules/sportapi/api/\"",
        )
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.common)

    implementation(libs.kotlinx.serializationJson)

    implementation(libs.okhttp)
    debugImplementation(libs.okhttp.logging.interceptor)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converterKotlinxSerialization)

    api(libs.sandwich)
    implementation(libs.sandwich.retrofit)

    implementation(libs.coil)
    implementation(libs.coil.networkOkhttp)
}
