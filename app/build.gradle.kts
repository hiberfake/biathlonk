plugins {
    alias(libs.plugins.biathlonk.android.application)
    alias(libs.plugins.biathlonk.spotless)
    alias(libs.plugins.androidx.baselineprofile)
    alias(libs.plugins.aboutlibraries)
    alias(libs.plugins.aboutlibraries.android)
}

android {
    namespace = "social.androiddev.hiberfake.biathlonk"

    defaultConfig {
        applicationId = "social.androiddev.hiberfake.biathlonk"

        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        androidResources.localeFilters += listOf("de")

        base.archivesName = "$applicationId-$versionName-build.$versionCode"
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.feature.leaderboard)
    implementation(projects.feature.schedule)
    implementation(projects.feature.settings)

    implementation(projects.core.common)
    implementation(projects.core.domain)
    implementation(projects.core.network)
    implementation(projects.core.ui)

    implementation(libs.androidx.profileinstaller)

    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.lifecycle.process)

    implementation(libs.androidx.window)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material3.adaptiveNavigationSuite)

    implementation(libs.coil)

//    baselineProfile(projects.benchmark)
}

baselineProfile {
    dexLayoutOptimization = true
    mergeIntoMain = true
    saveInSrc = true
}

aboutLibraries {
    collect.filterVariants = setOf(
        "debug",
        "release",
    )
}
