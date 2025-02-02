package social.androiddev.hiberfake.biathlonk.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import social.androiddev.hiberfake.biathlonk.leaderboard.navigation.navigateToLeaderboard
import social.androiddev.hiberfake.biathlonk.navigation.TopLevelDestination
import social.androiddev.hiberfake.biathlonk.navigation.TopLevelDestination.LEADERBOARD
import social.androiddev.hiberfake.biathlonk.navigation.TopLevelDestination.SCHEDULE
import social.androiddev.hiberfake.biathlonk.navigation.hasRouteInHierarchy
import social.androiddev.hiberfake.biathlonk.schedule.navigation.navigateToSchedule

@Composable
fun rememberBiathlonAppState(
    navController: NavHostController = rememberNavController(),
    startDestination: TopLevelDestination = SCHEDULE,
) = remember(
    navController,
    startDestination,
) {
    BiathlonAppState(
        navController = navController,
        startDestination = startDestination,
    )
}

@Stable
class BiathlonAppState(
    val navController: NavHostController,
    val startDestination: TopLevelDestination,
) {
//    private var previousDestination by mutableStateOf<NavDestination?>(null)

    val currentDestination: NavDestination?
        @Composable
        get() = navController.currentBackStackEntryAsState().value?.destination
//        get() {
//            // Collect the current navigation back stack entry as a state.
//            val currentBackStackEntry = navController.currentBackStackEntryAsState()
//
//            // Fallback to previousDestination if currentBackStackEntry is null.
//            return currentBackStackEntry.value?.destination
//                ?.also { previousDestination = it }
//                ?: previousDestination
//        }

    val currentTopLevelDestination
        @Composable
        get() = TopLevelDestination.entries.firstOrNull { destination ->
            currentDestination.hasRouteInHierarchy(destination.baseRoute)
        }

    /**
     * Navigates to the given top-level destination.
     *
     * This function handles different navigation scenarios:
     *  - If the current destination is already the target top-level destination, it scrolls to the
     *    top of the destination (TODO: Implement scroll-to-top behavior).
     *  - If the target top-level destination is already in the back stack, it pops the back stack
     *    back to that destination.
     *  - Otherwise, it navigates to the target top-level destination with appropriate [navOptions]
     *    to ensure a single top instance and save/restore state.
     *
     * @param destination The [TopLevelDestination] to navigate to.
     */
    fun navigateToTopLevelDestination(destination: TopLevelDestination) {
        val currentDestination = navController.currentDestination

        // Scroll to the top of the current top-level destination.
        if (currentDestination?.hasRoute(destination.route) == true) {
            /* no-op */
            return
        }

        // Pop the back stack back to the top-level destination.
        if (currentDestination.hasRouteInHierarchy(destination.baseRoute)) {
            navController.popBackStack(
                route = destination.route,
                inclusive = false,
            )
            return
        }

        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            launchSingleTop = true
            restoreState = true
        }

        when (destination) {
            SCHEDULE -> navController.navigateToSchedule(navOptions)
            LEADERBOARD -> navController.navigateToLeaderboard(navOptions)
        }
    }
}
