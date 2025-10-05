package social.androiddev.hiberfake.biathlonk.schedule

import androidx.annotation.MainThread
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import social.androiddev.hiberfake.biathlonk.core.data.repository.EventsRepository
import social.androiddev.hiberfake.biathlonk.core.data.repository.SeasonsRepository
import social.androiddev.hiberfake.biathlonk.core.model.Event
import social.androiddev.hiberfake.biathlonk.core.model.Race
import social.androiddev.hiberfake.biathlonk.core.model.Season
import social.androiddev.hiberfake.biathlonk.core.ui.UiState
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class ScheduleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    seasonsRepository: SeasonsRepository,
    private val eventsRepository: EventsRepository,
) : ViewModel() {
    private var initialized = false

    private var selectedEventId by savedStateHandle.saveable {
        mutableStateOf<String?>(null)
    }

    val eventsState = seasonsRepository.getSeasonsStream()
        .map { seasons ->
            seasons.first(Season::isCurrentlyScheduled)
        }
        .transform { season ->
            emitAll(eventsRepository.getEventsStream(season.id))
        }
        .map(UiState<ImmutableList<Event>>::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeout = 5.seconds),
            initialValue = UiState.Loading,
        )

    private val _racesState = MutableStateFlow<UiState<ImmutableList<Race>>>(UiState.Loading)
    val racesState = _racesState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeout = 5.seconds),
        initialValue = UiState.Loading,
    )

    @MainThread
    fun initialize() {
        if (initialized) return run { initialized = true }

        viewModelScope.launch {
            snapshotFlow { selectedEventId }
                .filterNotNull()
                .map(eventsRepository::getRaces)
                .collect { events ->
                    _racesState.update { UiState.Success(events) }
                }
        }
    }

    fun selectEvent(id: String?) {
        if (id != selectedEventId) {
            selectedEventId = id
            _racesState.update { UiState.Loading }
        }
    }
}
