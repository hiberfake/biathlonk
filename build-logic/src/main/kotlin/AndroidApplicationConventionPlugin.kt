import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import social.androiddev.hiberfake.biathlonk.configureAndroid
import social.androiddev.hiberfake.biathlonk.configureCompose
import social.androiddev.hiberfake.biathlonk.configureGradleManagedDevices
import social.androiddev.hiberfake.biathlonk.configureKotlin
import social.androiddev.hiberfake.biathlonk.libs

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.android.application.get().pluginId)
                apply(libs.plugins.kotlin.android.get().pluginId)
                apply(libs.plugins.kotlin.compose.get().pluginId)
                apply(libs.plugins.kotlin.serialization.get().pluginId)
                apply(libs.plugins.biathlonk.android.hilt.get().pluginId)
            }

            extensions.configure<KotlinAndroidProjectExtension> {
                configureKotlin(this)
            }

            extensions.configure<ApplicationExtension> {
                configureAndroid(this)
                configureCompose(this)
                configureGradleManagedDevices(this)

                defaultConfig {
                    targetSdk = libs.versions.targetSdk.get().toInt()
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
            }

            dependencies {
                "implementation"(libs.androidx.activity.compose)

                "implementation"(libs.androidx.lifecycle.runtimeCompose)
                "implementation"(libs.androidx.lifecycle.viewmodelCompose)

                "implementation"(libs.androidx.navigation.compose)

                "implementation"(libs.androidx.hilt.navigationCompose)

                "testImplementation"(kotlin("test"))
                "androidTestImplementation"(kotlin("test"))

                "testImplementation"(project(":core:testing"))
                "androidTestImplementation"(project(":core:testing"))
            }
        }
    }
}
