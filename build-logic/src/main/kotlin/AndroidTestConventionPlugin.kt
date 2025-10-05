import com.android.build.gradle.TestExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import social.androiddev.hiberfake.biathlonk.configureAndroid
import social.androiddev.hiberfake.biathlonk.configureGradleManagedDevices
import social.androiddev.hiberfake.biathlonk.configureKotlin
import social.androiddev.hiberfake.biathlonk.libs

@Suppress("unused")
class AndroidTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.android.test.get().pluginId)
                apply(libs.plugins.kotlin.android.get().pluginId)
            }

            extensions.configure<KotlinAndroidProjectExtension> {
                configureKotlin(this)
            }

            extensions.configure<TestExtension> {
                configureAndroid(this)
                configureGradleManagedDevices(this)

                defaultConfig {
                    targetSdk = libs.versions.targetSdk.get().toInt()
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
            }
        }
    }
}
