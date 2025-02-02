package social.androiddev.hiberfake.biathlonk.leaderboard

import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import social.androiddev.hiberfake.biathlonk.core.designsystem.theme.BiathlonTheme
import social.androiddev.hiberfake.biathlonk.core.model.Athlete
import social.androiddev.hiberfake.biathlonk.core.model.Category
import social.androiddev.hiberfake.biathlonk.core.model.CupResults
import social.androiddev.hiberfake.biathlonk.core.ui.AthleteItem
import social.androiddev.hiberfake.biathlonk.core.ui.ListItemDefaults
import social.androiddev.hiberfake.biathlonk.core.ui.UiState
import social.androiddev.hiberfake.biathlonk.core.ui.layout.plus

@Composable
internal fun LeaderboardRoute(
    modifier: Modifier = Modifier,
    viewModel: LeaderboardViewModel = hiltViewModel(),
) {
    val state by viewModel.cupResultsState.collectAsStateWithLifecycle()

    LeaderboardScreen(
        state = state,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LeaderboardScreen(
    state: UiState<ImmutableList<CupResults>>,
    modifier: Modifier = Modifier,
) {
    // UI is ready for use.
    ReportDrawnWhen { state !is UiState.Loading }

    val layoutDirection = LocalLayoutDirection.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var selectedIndex by remember { mutableIntStateOf(0) }
    val categories = remember { Category.entries }
    val dividerPadding = remember {
        with(ListItemDefaults) {
            PaddingValues(
                start = ContainerPadding.calculateStartPadding(layoutDirection)
                    .plus(LeadingAvatarSize)
                    .plus(Space),
            )
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    SingleChoiceSegmentedButtonRow {
                        categories.forEachIndexed { index, category ->
                            SegmentedButton(
                                selected = index == selectedIndex,
                                onClick = { selectedIndex = index },
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = categories.size,
                                ),
                            ) {
                                Text(
                                    text = when (category) {
                                        Category.SW -> "Frauen"
                                        Category.SM -> "Männer"
                                    },
                                )
                            }
                        }
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
            athletes(
                state = state,
                selectedIndex = selectedIndex,
                dividerModifier = Modifier.padding(dividerPadding),
            )
        }
    }
}

private fun LazyListScope.athletes(
    state: UiState<ImmutableList<CupResults>>,
    selectedIndex: Int,
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
                items = state.data[selectedIndex].rows,
                key = { _, athlete -> athlete.id },
                contentType = { _, _ -> Athlete::class },
            ) { index, athlete ->
                if (index > 0) {
                    HorizontalDivider(modifier = dividerModifier)
                }

                AthleteItem(athlete = athlete)
            }
        }

        is UiState.Failure -> Unit
    }
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(LeaderboardPreviewParameterProvider::class)
    state: UiState<ImmutableList<CupResults>>,
) {
    BiathlonTheme {
        LeaderboardScreen(state = state)
    }
}

private class LeaderboardPreviewParameterProvider : PreviewParameterProvider<UiState<ImmutableList<CupResults>>> {
    override val values = sequenceOf(
        UiState.Loading,
        UiState.Success(persistentListOf(CupResults.preview)),
    )
}
