package social.androiddev.hiberfake.biathlonk.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.rememberNavigationSuiteScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    val currentDestination = appState.currentDestination
    val currentTopLevelDestination = appState.currentTopLevelDestination
    val state = rememberNavigationSuiteScaffoldState()

    LaunchedEffect(currentDestination) {
        if (currentDestination.hasTopLevelDestination()) state.show() else state.hide()
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
        modifier = modifier,
        state = state,
    ) {
        BiathlonNavHost(
            startDestination = appState.startDestination.baseRoute,
            navController = appState.navController,
        )
    }
}
