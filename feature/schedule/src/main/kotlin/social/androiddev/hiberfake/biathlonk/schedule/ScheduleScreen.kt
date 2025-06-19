package social.androiddev.hiberfake.biathlonk.schedule

import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import social.androiddev.hiberfake.biathlonk.core.designsystem.theme.BiathlonTheme
import social.androiddev.hiberfake.biathlonk.core.model.Event
import social.androiddev.hiberfake.biathlonk.core.model.Race
import social.androiddev.hiberfake.biathlonk.core.ui.EventItem
import social.androiddev.hiberfake.biathlonk.core.ui.ListItemDefaults
import social.androiddev.hiberfake.biathlonk.core.ui.RaceItem
import social.androiddev.hiberfake.biathlonk.core.ui.UiState
import social.androiddev.hiberfake.biathlonk.core.ui.icons.Icons
import social.androiddev.hiberfake.biathlonk.core.ui.icons.filled.Settings
import social.androiddev.hiberfake.biathlonk.core.ui.layout.plus
import social.androiddev.hiberfake.biathlonk.core.ui.R as uiR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ScheduleRoute(
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ScheduleViewModel = hiltViewModel(),
) {
    val eventsState by viewModel.eventsState.collectAsStateWithLifecycle()
    val racesState by viewModel.racesState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    ScheduleScreen(
        eventsState = eventsState,
        racesState = racesState,
        onEventClick = viewModel::selectEvent,
        onSettingsClick = onNavigateToSettings,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScheduleScreen(
    eventsState: UiState<ImmutableList<Event>>,
    racesState: UiState<ImmutableList<Race>>,
    onEventClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // UI is ready for use.
    ReportDrawnWhen { eventsState !is UiState.Loading }

    val layoutDirection = LocalLayoutDirection.current
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val dividerPadding = remember {
        with(ListItemDefaults) {
            PaddingValues(
                start = ContainerPadding.calculateStartPadding(layoutDirection),
                end = ContainerPadding.calculateEndPadding(layoutDirection),
            )
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = stringResource(uiR.string.action_settings),
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding.plus(bottom = 8.dp),
        ) {
            events(
                state = eventsState,
                onClick = { id ->
                    onEventClick(id)
                    scope.launch { sheetState.show() }
                },
                dividerModifier = Modifier.padding(dividerPadding),
            )
        }
    }

    if (sheetState.isVisible) {
        ModalBottomSheet(
            onDismissRequest = { scope.launch { sheetState.hide() } },
            modifier = Modifier.statusBarsPadding(),
            sheetState = sheetState,
        ) {
            LazyColumn(
//                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 8.dp),
            ) {
                races(
                    state = racesState,
                    dividerModifier = Modifier.padding(dividerPadding),
                )
            }
        }
    }
}

private fun LazyListScope.events(
    state: UiState<ImmutableList<Event>>,
    onClick: (String) -> Unit,
    dividerModifier: Modifier = Modifier,
) {
    when (state) {
        UiState.Loading -> {
            item {
                Box(
                    modifier = Modifier.fillParentMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        is UiState.Success -> {
            itemsIndexed(
                items = state.data,
                key = { _, event -> event.id },
                contentType = { _, _ -> Event::class },
            ) { index, event ->
                if (index > 0) {
                    HorizontalDivider(modifier = dividerModifier)
                }

                EventItem(
                    event = event,
                    onClick = { onClick(event.id) },
                )
            }
        }

        is UiState.Failure -> Unit
    }
}

private fun LazyListScope.races(
    state: UiState<ImmutableList<Race>>,
    dividerModifier: Modifier = Modifier,
) {
    when (state) {
        UiState.Loading -> {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillParentMaxHeight(fraction = .5f),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        is UiState.Success -> {
            itemsIndexed(
                items = state.data,
                key = { _, race -> race.id },
                contentType = { _, _ -> Race::class },
            ) { index, race ->
                if (index > 0) {
                    HorizontalDivider(modifier = dividerModifier)
                }

                RaceItem(race = race)
            }
        }

        is UiState.Failure -> Unit
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun Preview(
    @PreviewParameter(SchedulePreviewParameterProvider::class)
    states: Pair<UiState<ImmutableList<Event>>, UiState<ImmutableList<Race>>>,
) {
    val (eventsState, racesState) = states
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(Unit) {
        sheetState.show()
    }

    BiathlonTheme {
        ScheduleScreen(
            eventsState = eventsState,
            racesState = racesState,
            onEventClick = {},
            onSettingsClick = {},
        )
    }
}

private class SchedulePreviewParameterProvider :
    PreviewParameterProvider<Pair<UiState<ImmutableList<Event>>, UiState<ImmutableList<Race>>>> {
    override val values = sequenceOf(
        UiState.Loading to UiState.Loading,
        UiState.Success(persistentListOf(Event.preview)) to UiState.Success(persistentListOf(Race.preview)),
    )
}
