package social.androiddev.hiberfake.biathlonk

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ManagedVirtualDevice
import org.gradle.kotlin.dsl.create

internal fun configureGradleManagedDevices(extension: CommonExtension<*, *, *, *, *, *>) {
    extension.testOptions {
        managedDevices {
            allDevices.create<ManagedVirtualDevice>(pixel6Api35.name) {
                device = pixel6Api35.device
                apiLevel = pixel6Api35.apiLevel
                systemImageSource = pixel6Api35.systemImageSource
            }
        }
    }
}

data class GradleManagedDevice(
    val device: String,
    val apiLevel: Int,
    val systemImageSource: String,
) {
    val name = buildString {
        append(device.lowercase().replace(" ", ""))
        append("Api")
        append(apiLevel)
    }
}

val pixel6Api35 = GradleManagedDevice(
    device = "Pixel 6",
    apiLevel = 35,
    systemImageSource = "aosp-atd",
)
