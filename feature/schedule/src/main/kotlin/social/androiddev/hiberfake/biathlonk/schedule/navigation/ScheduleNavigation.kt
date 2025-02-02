package social.androiddev.hiberfake.biathlonk.schedule.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import social.androiddev.hiberfake.biathlonk.schedule.ScheduleRoute

@Serializable
data object ScheduleRoute

@Serializable
data object ScheduleBaseRoute

fun NavGraphBuilder.scheduleScreen(
    onNavigateToSettings: () -> Unit,
    nestedGraph: NavGraphBuilder.() -> Unit = {},
) {
    navigation<ScheduleBaseRoute>(startDestination = ScheduleRoute) {
        composable<ScheduleRoute> {
            ScheduleRoute(onNavigateToSettings = onNavigateToSettings)
        }

        nestedGraph()
    }
}

fun NavController.navigateToSchedule(navOptions: NavOptions? = null) {
    navigate(
        route = ScheduleRoute,
        navOptions = navOptions,
    )
}
