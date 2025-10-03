package social.androiddev.hiberfake.biathlonk

import com.android.build.gradle.TestedExtension
import org.gradle.api.Project
import org.gradle.api.tasks.testing.AbstractTestTask
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

internal fun Project.configureUnitTests(extension: TestedExtension) {
    extension.apply {
        testOptions {
            unitTests.apply {
                isReturnDefaultValues = true
                isIncludeAndroidResources = true
            }
        }
    }

    val byteBuddyAgent = configurations.register("byteBuddyAgent").get()

    dependencies {
        byteBuddyAgent(libs.bytebuddy.agent)
    }

    tasks.apply {
        withType<Test>().configureEach {
            jvmArgs("-javaagent:${byteBuddyAgent.asPath}")
        }

        withType<AbstractTestTask>().configureEach {
            failOnNoDiscoveredTests.set(false)
        }
    }
}
