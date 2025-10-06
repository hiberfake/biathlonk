package social.androiddev.hiberfake.biathlonk

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

internal fun Project.configureKotlin(extension: KotlinAndroidProjectExtension) {
    extension.apply {
        jvmToolchain(21)

        compilerOptions {
            freeCompilerArgs.apply {
                add("-Xannotation-default-target=param-property")

                add("-opt-in=kotlin.time.ExperimentalTime")
                add("-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi")
                add("-opt-in=androidx.compose.material3.ExperimentalMaterial3Api")
            }
        }
    }

    dependencies {
        "implementation"(libs.kotlinx.collectionsImmutable)
        "implementation"(libs.kotlinx.datetime)

//        "implementation"(libs.flowOperators)
    }
}

