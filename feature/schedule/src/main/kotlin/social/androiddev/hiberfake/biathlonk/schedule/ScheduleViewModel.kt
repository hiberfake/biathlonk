package social.androiddev.hiberfake.biathlonk.schedule

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.flow.update
import social.androiddev.hiberfake.biathlonk.core.data.repository.EventsRepository
import social.androiddev.hiberfake.biathlonk.core.domain.usecase.GetEventsUseCase
import social.androiddev.hiberfake.biathlonk.core.model.Event
import social.androiddev.hiberfake.biathlonk.core.ui.UiState
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getEventsUseCase: GetEventsUseCase,
    private val eventsRepository: EventsRepository,
) : ViewModel() {
    private val selectedEventId = savedStateHandle.getMutableStateFlow<String?>(
        key = "selectedEventId",
        initialValue = null,
    )

    val eventsState = getEventsUseCase()
        .map(UiState<ImmutableList<Event>>::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeout = 5.seconds),
            initialValue = UiState.Loading,
        )

    val racesState = selectedEventId
        .filterNotNull()
        .transformLatest { eventId ->
            emit(UiState.Loading)
            emit(UiState.Success(eventsRepository.getRaces(eventId)))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeout = 5.seconds),
            initialValue = UiState.Loading,
        )

    fun selectEvent(id: String) = selectedEventId.update { id }
}
