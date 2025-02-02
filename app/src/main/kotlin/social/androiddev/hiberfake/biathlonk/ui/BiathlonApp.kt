package social.androiddev.hiberfake.biathlonk.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.util.fastForEach
import social.androiddev.hiberfake.biathlonk.navigation.BiathlonNavHost
import social.androiddev.hiberfake.biathlonk.navigation.TopLevelDestination
import social.androiddev.hiberfake.biathlonk.navigation.hasTopLevelDestination

@Composable
fun BiathlonApp(
    modifier: Modifier = Modifier,
    appState: BiathlonAppState = rememberBiathlonAppState(),
) {
    val shouldShowNavigation = appState.currentDestination.hasTopLevelDestination()
    val currentTopLevelDestination = appState.currentTopLevelDestination
    val adaptiveInfo = currentWindowAdaptiveInfo()
    val layoutType = when {
        shouldShowNavigation.not() -> NavigationSuiteType.None
        else -> NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)
    }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            TopLevelDestination.entries.fastForEach { destination ->
                val selected = destination == currentTopLevelDestination

                item(
                    selected = selected,
                    onClick = { appState.navigateToTopLevelDestination(destination) },
                    icon = {
                        Icon(
                            imageVector = if (selected) {
                                destination.selectedIcon
                            } else {
                                destination.unselectedIcon
                            },
                            contentDescription = stringResource(destination.labelId),
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(destination.labelId),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                        )
                    },
                )
            }
        },
        modifier = modifier.animateContentSize(),
        layoutType = layoutType,
    ) {
        BiathlonNavHost(
            startDestination = appState.startDestination.baseRoute,
            navController = appState.navController,
        )
    }
}
