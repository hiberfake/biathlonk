package social.androiddev.hiberfake.biathlonk.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import social.androiddev.hiberfake.biathlonk.R
import social.androiddev.hiberfake.biathlonk.core.ui.icons.Icons
import social.androiddev.hiberfake.biathlonk.core.ui.icons.filled.Leaderboard
import social.androiddev.hiberfake.biathlonk.core.ui.icons.filled.Schedule
import social.androiddev.hiberfake.biathlonk.core.ui.icons.outline.Leaderboard
import social.androiddev.hiberfake.biathlonk.core.ui.icons.outline.Schedule
import social.androiddev.hiberfake.biathlonk.leaderboard.navigation.LeaderboardRoute
import social.androiddev.hiberfake.biathlonk.schedule.navigation.ScheduleBaseRoute
import social.androiddev.hiberfake.biathlonk.schedule.navigation.ScheduleRoute
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val labelId: Int,
    val route: KClass<*>,
    val baseRoute: KClass<*> = route,
) {
    SCHEDULE(
        selectedIcon = Icons.Filled.Schedule,
        unselectedIcon = Icons.Outlined.Schedule,
        labelId = R.string.navigation_schedule,
        route = ScheduleRoute::class,
        baseRoute = ScheduleBaseRoute::class,
    ),
    LEADERBOARD(
        selectedIcon = Icons.Filled.Leaderboard,
        unselectedIcon = Icons.Outlined.Leaderboard,
        labelId = R.string.navigation_leaderboard,
        route = LeaderboardRoute::class,
    ),
}
