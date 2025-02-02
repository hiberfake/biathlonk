package social.androiddev.hiberfake.biathlonk.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import social.androiddev.hiberfake.biathlonk.leaderboard.navigation.leaderboardScreen
import social.androiddev.hiberfake.biathlonk.schedule.navigation.scheduleScreen
import social.androiddev.hiberfake.biathlonk.settings.navigation.navigateToLibraries
import social.androiddev.hiberfake.biathlonk.settings.navigation.navigateToSettings
import social.androiddev.hiberfake.biathlonk.settings.navigation.settingsScreen
import kotlin.reflect.KClass

@Composable
fun BiathlonNavHost(
    startDestination: KClass<*>,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        // Top-level destinations
        scheduleScreen(navController::navigateToSettings)
        leaderboardScreen()

        // Settings
        settingsScreen(
            onNavigateUp = navController::navigateUp,
            onNavigateToLibraries = navController::navigateToLibraries,
        )
    }
}
