package social.androiddev.hiberfake.biathlonk.schedule

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.testing.viewModelScenario
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import social.androiddev.hiberfake.biathlonk.core.data.repository.EventsRepository
import social.androiddev.hiberfake.biathlonk.core.data.repository.SeasonsRepository
import social.androiddev.hiberfake.biathlonk.core.ui.UiState
import kotlin.test.assertEquals

class ScheduleViewModelTest {
    val mockSeasonsRepository = mockk<SeasonsRepository>(relaxed = true)
    val mockEventsRepository = mockk<EventsRepository>(relaxed = true)

    @Test
    fun eventsState_isLoading_whenInitialized() = runTest {
        viewModelScenario {
            ScheduleViewModel(
                savedStateHandle = SavedStateHandle(),
                seasonsRepository = mockSeasonsRepository,
                eventsRepository = mockEventsRepository,
            )
        }.use {
            it.viewModel.initialize()

            assertEquals(UiState.Loading, it.viewModel.eventsState.value)
        }
    }
}
