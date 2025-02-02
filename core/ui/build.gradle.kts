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

//    api(libs.androidx.compose.material.iconsExtended)

    api(libs.coil.compose)

//    implementation(libs.compose.richtext.commonmark) {
//        // TODO: Migrate to Material3.
//        //  https://github.com/halilozercan/compose-richtext/issues/139
//        exclude(group = "androidx.compose.material3")
//        exclude(group = "io.coil-kt")
//    }
//    implementation(libs.compose.richtext.ui.material)
}
