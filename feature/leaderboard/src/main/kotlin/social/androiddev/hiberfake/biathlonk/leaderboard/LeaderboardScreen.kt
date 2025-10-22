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
import androidx.compose.ui.util.fastForEachIndexed
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import social.androiddev.hiberfake.biathlonk.core.designsystem.theme.BiathlonTheme
import social.androiddev.hiberfake.biathlonk.core.model.Athlete
import social.androiddev.hiberfake.biathlonk.core.model.Category
import social.androiddev.hiberfake.biathlonk.core.model.CupResults
import social.androiddev.hiberfake.biathlonk.core.ui.AthleteItem
import social.androiddev.hiberfake.biathlonk.core.ui.BiathlonListItemDefaults
import social.androiddev.hiberfake.biathlonk.core.ui.R
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

@Composable
private fun LeaderboardScreen(
    state: UiState<ImmutableList<CupResults>>,
    modifier: Modifier = Modifier,
) {
    // UI is ready for use.
    ReportDrawnWhen { state !is UiState.Loading }

    val layoutDirection = LocalLayoutDirection.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    val categories = remember { Category.entries }
    val dividerPadding = remember {
        with(BiathlonListItemDefaults) {
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
                        categories.fastForEachIndexed { index, category ->
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
                                        Category.SINGLE_WOMEN -> stringResource(R.string.category_women)
                                        Category.SINGLE_MEN -> stringResource(R.string.category_men)
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
            val athletes = state.data[selectedIndex].athletes

            itemsIndexed(
                items = athletes,
                key = { _, athlete -> athlete.id },
                contentType = { _, _ -> Athlete::class },
            ) { index, athlete ->
                AthleteItem(athlete = athlete)

                if (index < athletes.lastIndex) {
                    HorizontalDivider(modifier = dividerModifier)
                }
            }
        }

        is UiState.Failure -> Unit
    }
}

@PreviewLightDark
@Composable
private fun LeaderboardScreenPreview(
    @PreviewParameter(LeaderboardPreviewParameterProvider::class)
    state: UiState<ImmutableList<CupResults>>,
) = BiathlonTheme {
    LeaderboardScreen(state = state)
}

private class LeaderboardPreviewParameterProvider : PreviewParameterProvider<UiState<ImmutableList<CupResults>>> {
    override val values = sequenceOf(
        UiState.Loading,
        UiState.Success(persistentListOf(CupResults.preview)),
    )
}
