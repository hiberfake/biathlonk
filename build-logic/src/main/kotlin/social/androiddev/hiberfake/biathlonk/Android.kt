package social.androiddev.hiberfake.biathlonk

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

internal fun Project.configureAndroid(extension: CommonExtension<*, *, *, *, *, *>) {
    extension.apply {
        compileSdk = libs.versions.compileSdk.get().toInt()

        defaultConfig {
            minSdk = libs.versions.minSdk.get().toInt()
        }

        compileOptions {
            isCoreLibraryDesugaringEnabled = true

            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
        }

        lint {
            checkDependencies = true
            warningsAsErrors = true
        }

        testOptions {
            unitTests.isReturnDefaultValues = true
        }
    }

    if (extension is KotlinAndroidProjectExtension) {
        extension.compilerOptions {
            jvmTarget = JvmTarget.JVM_21
        }
    }

    dependencies {
        "coreLibraryDesugaring"(libs.android.desugar)

        "implementation"(libs.kotlinx.coroutinesAndroid)

        "implementation"(libs.timber)
    }
}
