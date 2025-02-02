package social.androiddev.hiberfake.biathlonk

import com.android.build.api.variant.LibraryAndroidComponentsExtension
import org.gradle.api.Project

/**
 * Disables unnecessary Android instrumented tests for a project if there is no `androidTest` folder.
 * Otherwise, those projects would be compiled, packaged, installed and ran only to end up with the following message:
 *
 * > Starting 0 tests on AVD
 */
internal fun Project.disableUnnecessaryAndroidTests(extension: LibraryAndroidComponentsExtension) {
    extension.beforeVariants {
        it.androidTest.enable = it.androidTest.enable && projectDir.resolve("src/androidTest").exists()
    }
}
