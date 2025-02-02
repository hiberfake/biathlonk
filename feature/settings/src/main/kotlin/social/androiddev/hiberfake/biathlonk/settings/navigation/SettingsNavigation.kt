package social.androiddev.hiberfake.biathlonk.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import social.androiddev.hiberfake.biathlonk.settings.SettingsRoute
import social.androiddev.hiberfake.biathlonk.settings.libraries.LibrariesRoute

@Serializable
data object SettingsRoute

@Serializable
data object SettingsBaseRoute

@Serializable
data object LibrariesRoute

fun NavGraphBuilder.settingsScreen(
    onNavigateUp: () -> Unit,
    onNavigateToLibraries: () -> Unit,
) {
    navigation<SettingsBaseRoute>(startDestination = SettingsRoute) {
        composable<SettingsRoute> {
            SettingsRoute(
                onNavigateUp = onNavigateUp,
                onNavigateToLibraries = onNavigateToLibraries,
            )
        }

        librariesScreen(onNavigateUp)
    }
}

private fun NavGraphBuilder.librariesScreen(onNavigateUp: () -> Unit) {
    composable<LibrariesRoute> {
        LibrariesRoute(onNavigateUp = onNavigateUp)
    }
}

fun NavController.navigateToSettings(navOptions: NavOptions? = null) {
    navigate(
        route = SettingsRoute,
        navOptions = navOptions,
    )
}

fun NavController.navigateToLibraries(navOptions: NavOptions? = null) {
    navigate(
        route = LibrariesRoute,
        navOptions = navOptions,
    )
}
