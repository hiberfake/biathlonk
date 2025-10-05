import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import social.androiddev.hiberfake.biathlonk.libs

@Suppress("unused")
class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.kotlin.serialization.get().pluginId)
                apply(libs.plugins.biathlonk.android.library.compose.get().pluginId)
                apply(libs.plugins.biathlonk.android.hilt.get().pluginId)
            }

            dependencies {
                "implementation"(project(":core:data"))
                "implementation"(project(":core:ui"))

                "implementation"(libs.androidx.lifecycle.runtimeCompose)
                "implementation"(libs.androidx.lifecycle.viewmodelCompose)

                "implementation"(libs.androidx.navigation.compose)

                "implementation"(libs.androidx.hilt.navigationCompose)

                "testImplementation"(libs.androidx.lifecycle.viewmodelTesting)
            }
        }
    }
}
