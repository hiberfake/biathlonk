plugins {
    `kotlin-dsl`
    alias(libs.plugins.android.lint)
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.plugin)
    compileOnly(libs.kotlin.compose.plugin)
    compileOnly(libs.hilt.android.plugin)
    compileOnly(libs.spotless.plugin)

    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    lintChecks(libs.androidx.lint)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "biathlonk.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidFeature") {
            id = "biathlonk.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidLibrary") {
            id = "biathlonk.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "biathlonk.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidHilt") {
            id = "biathlonk.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidTest") {
            id = "biathlonk.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }
        register("spotless") {
            id = "biathlonk.spotless"
            implementationClass = "SpotlessConventionPlugin"
        }
    }
}
