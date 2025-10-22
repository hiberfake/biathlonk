package social.androiddev.hiberfake.biathlonk

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidExtension

internal fun Project.configureCompose(extension: CommonExtension<*, *, *, *, *, *>) {
    extension.apply {
        buildFeatures {
            compose = true
        }

        lint {
            warning += "AutoboxingStateCreation"
        }

        dependencies {
            val bom = platform(libs.androidx.compose.bom)

            "implementation"(bom)
            "debugImplementation"(libs.androidx.compose.ui.tooling)
            "debugImplementation"(libs.androidx.compose.ui.toolingPreview)

            "androidTestImplementation"(bom)
            "androidTestImplementation"(libs.androidx.compose.ui.testJunit4)
            "androidTestImplementation"(libs.androidx.compose.ui.testManifest)

            "lintChecks"(libs.compose.lints)
        }
    }

    extensions.configure<KotlinAndroidExtension> {
        compilerOptions {
            freeCompilerArgs.apply {
                add("-opt-in=androidx.compose.material3.ExperimentalMaterial3Api")
                add("-opt-in=androidx.compose.material3.ExperimentalMaterial3ExpressiveApi")
            }
        }
    }

    extensions.configure<ComposeCompilerGradlePluginExtension> {
        fun Provider<String>.onlyIfTrue() = flatMap { provider { it.takeIf(String::toBoolean) } }
        fun Provider<*>.relativeToRootProject(dir: String) = map {
            isolated.rootProject.projectDirectory
                .dir(Project.DEFAULT_BUILD_DIR_NAME)
                .dir(projectDir.toRelativeString(rootDir))
        }.map { it.dir(dir) }

        project.providers.gradleProperty("enableComposeCompilerMetrics").onlyIfTrue()
            .relativeToRootProject("compose-metrics")
            .let(metricsDestination::set)

        project.providers.gradleProperty("enableComposeCompilerReports").onlyIfTrue()
            .relativeToRootProject("compose-reports")
            .let(reportsDestination::set)

        stabilityConfigurationFiles.add(isolated.rootProject.projectDirectory.file("compose-compiler-config.conf"))
    }
}
