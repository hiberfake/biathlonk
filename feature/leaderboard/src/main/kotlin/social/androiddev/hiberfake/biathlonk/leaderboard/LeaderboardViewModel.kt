package social.androiddev.hiberfake.biathlonk.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import social.androiddev.hiberfake.biathlonk.core.data.repository.ResultsRepository
import social.androiddev.hiberfake.biathlonk.core.data.repository.SeasonsRepository
import social.androiddev.hiberfake.biathlonk.core.model.CupResults
import social.androiddev.hiberfake.biathlonk.core.model.Season
import social.androiddev.hiberfake.biathlonk.core.ui.UiState
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    seasonsRepository: SeasonsRepository,
    resultsRepository: ResultsRepository,
) : ViewModel() {
    val cupResultsState = seasonsRepository.getSeasonsStream()
        .map { seasons ->
            seasons.first(Season::hasCurrentResults)
        }
        .transform { season ->
            emitAll(resultsRepository.getCupResultsStream(season.id))
        }
        .map(UiState<ImmutableList<CupResults>>::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeout = 5.seconds),
            initialValue = UiState.Loading,
        )
}
