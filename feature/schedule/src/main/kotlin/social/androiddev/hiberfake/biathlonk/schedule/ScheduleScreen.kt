package social.androiddev.hiberfake.biathlonk.schedule

import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.runBlocking
import social.androiddev.hiberfake.biathlonk.core.designsystem.theme.BiathlonTheme
import social.androiddev.hiberfake.biathlonk.core.model.Event
import social.androiddev.hiberfake.biathlonk.core.model.Race
import social.androiddev.hiberfake.biathlonk.core.ui.BiathlonListItemDefaults
import social.androiddev.hiberfake.biathlonk.core.ui.EventItem
import social.androiddev.hiberfake.biathlonk.core.ui.RaceItem
import social.androiddev.hiberfake.biathlonk.core.ui.UiState
import social.androiddev.hiberfake.biathlonk.core.ui.icons.Icons
import social.androiddev.hiberfake.biathlonk.core.ui.icons.filled.Settings
import social.androiddev.hiberfake.biathlonk.core.ui.layout.plus
import social.androiddev.hiberfake.biathlonk.core.ui.R as uiR

@Composable
internal fun ScheduleRoute(
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ScheduleViewModel = hiltViewModel(),
) {
    val eventsState by viewModel.eventsState.collectAsStateWithLifecycle()
    val racesState by viewModel.racesState.collectAsStateWithLifecycle()

    ScheduleScreen(
        eventsState = eventsState,
        racesState = racesState,
        onEventClick = viewModel::selectEvent,
        onSettingsClick = onNavigateToSettings,
        modifier = modifier,
    )
}

@Composable
private fun ScheduleScreen(
    eventsState: UiState<ImmutableList<Event>>,
    racesState: UiState<ImmutableList<Race>>,
    onEventClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
) {
    // UI is ready for use.
    ReportDrawnWhen { eventsState !is UiState.Loading }

    val layoutDirection = LocalLayoutDirection.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val dividerPadding = remember {
        with(BiathlonListItemDefaults) {
            PaddingValues(
                start = ContainerPadding.calculateStartPadding(layoutDirection),
                end = ContainerPadding.calculateEndPadding(layoutDirection),
            )
        }
    }
    var showBottomSheet by rememberSaveable { mutableStateOf(sheetState.isVisible) }

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
        contentWindowInsets = WindowInsets.safeDrawing,
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding.plus(bottom = 8.dp),
        ) {
            events(
                state = eventsState,
                onClick = { id ->
                    onEventClick(id)
                    showBottomSheet = true
                },
                dividerModifier = Modifier.padding(dividerPadding),
            )
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
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
            val events = state.data

            itemsIndexed(
                items = events,
                key = { _, event -> event.id },
                contentType = { _, _ -> Event::class },
            ) { index, event ->
                EventItem(
                    event = event,
                    onClick = { onClick(event.id) },
                )

                if (index < events.lastIndex) {
                    HorizontalDivider(modifier = dividerModifier)
                }
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
                        .requiredHeightIn(min = BiathlonListItemDefaults.TwoLinesListItemContainerHeight),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        is UiState.Success -> {
            val races = state.data

            itemsIndexed(
                items = races,
                key = { _, race -> race.id },
                contentType = { _, _ -> Race::class },
            ) { index, race ->
                RaceItem(race = race)

                if (index < state.data.lastIndex) {
                    HorizontalDivider(modifier = dividerModifier)
                }
            }
        }

        is UiState.Failure -> Unit
    }
}

@PreviewLightDark
@Composable
private fun ScheduleScreenPreview(
    @PreviewParameter(EventsStatePreviewParameterProvider::class)
    eventsState: UiState<ImmutableList<Event>>,
) = BiathlonTheme {
    ScheduleScreen(
        eventsState = eventsState,
        racesState = UiState.Loading,
        onEventClick = {},
        onSettingsClick = {},
    )
}

@PreviewLightDark
@Composable
private fun ScheduleScreenWithModalBottomSheetPreview(
    @PreviewParameter(RacesStatePreviewParameterProvider::class)
    racesState: UiState<ImmutableList<Race>>,
) = BiathlonTheme {
    val sheetState = rememberModalBottomSheetState()

    runBlocking { sheetState.show() }

    ScheduleScreen(
        eventsState = UiState.Loading,
        racesState = racesState,
        onEventClick = {},
        onSettingsClick = {},
        sheetState = sheetState,
    )
}

private class EventsStatePreviewParameterProvider : PreviewParameterProvider<UiState<ImmutableList<Event>>> {
    override val values = sequenceOf(
        UiState.Loading,
        UiState.Success(persistentListOf(Event.preview)),
    )
}

private class RacesStatePreviewParameterProvider : PreviewParameterProvider<UiState<ImmutableList<Race>>> {
    override val values = sequenceOf(
        UiState.Loading,
        UiState.Success(persistentListOf(Race.preview)),
    )
}
