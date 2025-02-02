import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import social.androiddev.hiberfake.biathlonk.configureAndroid
import social.androiddev.hiberfake.biathlonk.configureGradleManagedDevices
import social.androiddev.hiberfake.biathlonk.configureKotlin
import social.androiddev.hiberfake.biathlonk.disableUnnecessaryAndroidTests
import social.androiddev.hiberfake.biathlonk.libs

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.android.library.get().pluginId)
                apply(libs.plugins.kotlin.android.get().pluginId)
            }

            extensions.configure<KotlinAndroidProjectExtension> {
                configureKotlin(this)
            }

            extensions.configure<LibraryExtension> {
                configureAndroid(this)
                configureGradleManagedDevices(this)

                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    testOptions.targetSdk = libs.versions.targetSdk.get().toInt()
                }
            }

            extensions.configure<LibraryAndroidComponentsExtension> {
                disableUnnecessaryAndroidTests(this)
            }

            dependencies {
                "implementation"(libs.androidx.annotation)
                "implementation"(libs.androidx.core)

                "testImplementation"(kotlin("test"))
                "androidTestImplementation"(kotlin("test"))

                "testImplementation"(project(":core:testing"))
                "androidTestImplementation"(project(":core:testing"))

                "testImplementation"(libs.mockk)
                "androidTestImplementation"(libs.mockk.android)
            }
        }
    }
}
