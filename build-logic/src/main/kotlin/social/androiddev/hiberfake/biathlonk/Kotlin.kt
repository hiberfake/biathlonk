package social.androiddev.hiberfake.biathlonk

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

internal fun Project.configureKotlin(extension: KotlinAndroidProjectExtension) {
    extension.jvmToolchain(21)

    dependencies {
        "implementation"(libs.kotlinx.collectionsImmutable)
        "implementation"(libs.kotlinx.datetime)

//        "implementation"(libs.flowOperators)
    }
}

