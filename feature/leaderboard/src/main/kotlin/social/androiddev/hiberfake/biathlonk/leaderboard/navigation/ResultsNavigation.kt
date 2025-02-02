package social.androiddev.hiberfake.biathlonk.leaderboard.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import social.androiddev.hiberfake.biathlonk.leaderboard.LeaderboardRoute

@Serializable
data object LeaderboardRoute

fun NavGraphBuilder.leaderboardScreen() {
    composable<LeaderboardRoute> {
        LeaderboardRoute()
    }
}

fun NavController.navigateToLeaderboard(navOptions: NavOptions? = null) {
    navigate(
        route = LeaderboardRoute,
        navOptions = navOptions,
    )
}
