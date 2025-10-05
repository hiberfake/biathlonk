package social.androiddev.hiberfake.biathlonk.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import social.androiddev.hiberfake.biathlonk.core.domain.usecase.GetCupResultsUseCase
import social.androiddev.hiberfake.biathlonk.core.model.CupResults
import social.androiddev.hiberfake.biathlonk.core.ui.UiState
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class LeaderboardViewModel @Inject constructor(getCupResultsUseCase: GetCupResultsUseCase) : ViewModel() {
    val cupResultsState = getCupResultsUseCase()
        .map(UiState<ImmutableList<CupResults>>::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeout = 5.seconds),
            initialValue = UiState.Loading,
        )
}
