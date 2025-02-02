import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import social.androiddev.hiberfake.biathlonk.configureCompose
import social.androiddev.hiberfake.biathlonk.libs

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.kotlin.compose.get().pluginId)
                apply(libs.plugins.biathlonk.android.library.asProvider().get().pluginId)
            }

            extensions.configure<LibraryExtension> {
                configureCompose(this)
            }
        }
    }
}
