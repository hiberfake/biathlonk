package social.androiddev.hiberfake.biathlonk.schedule

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.testing.ViewModelScenario
import androidx.lifecycle.viewmodel.testing.viewModelScenario
import androidx.test.filters.LargeTest
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import social.androiddev.hiberfake.biathlonk.core.data.repository.EventsRepository
import social.androiddev.hiberfake.biathlonk.core.domain.usecase.GetEventsUseCase
import social.androiddev.hiberfake.biathlonk.core.model.Race
import social.androiddev.hiberfake.biathlonk.core.testing.MainDispatcherRule
import social.androiddev.hiberfake.biathlonk.core.testing.eventsTestData
import social.androiddev.hiberfake.biathlonk.core.testing.racesTestData
import social.androiddev.hiberfake.biathlonk.core.ui.UiState
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

@RunWith(RobolectricTestRunner::class)
@LargeTest
class ScheduleViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    val mockGetEventsUseCase = mockk<GetEventsUseCase>(relaxed = true)
    val mockEventsRepository = mockk<EventsRepository>(relaxed = true)

    lateinit var viewModelScenario: ViewModelScenario<ScheduleViewModel>

    @BeforeTest
    fun setup() {
        viewModelScenario = viewModelScenario {
            ScheduleViewModel(
                savedStateHandle = createSavedStateHandle(),
                getEventsUseCase = mockGetEventsUseCase,
                eventsRepository = mockEventsRepository,
            )
        }
    }

    @Test
    fun eventsState_isLoading_whenInitialized() = runTest {
        viewModelScenario.use {
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                it.viewModel.eventsState.collect {}
            }

            it.viewModel.eventsState.test {
                assertEquals(UiState.Loading, awaitItem())
            }
        }
    }

    @Test
    fun racesState_isLoading_whenInitialized() = runTest {
        viewModelScenario.use {
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                it.viewModel.eventsState.collect {}
            }

            it.viewModel.racesState.test {
                assertEquals(UiState.Loading, awaitItem())
            }
        }
    }

    @Test
    fun racesState_isSuccess_whenEventSelected() = runTest {
        val eventId = eventsTestData.first().id

        coEvery { mockEventsRepository.getRaces(eventId) } returns racesTestData

        viewModelScenario.use {
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                it.viewModel.racesState.collect {}
            }

            it.viewModel.racesState.test {
                assertEquals(UiState.Loading, awaitItem())

                it.viewModel.selectEvent(eventId)

                with(awaitItem()) {
                    assertIs<UiState.Success<ImmutableList<Race>>>(this)
                    assertEquals(racesTestData, data)
                }
            }

            it.recreate()

            it.viewModel.racesState.test {
                with(awaitItem()) {
                    assertIs<UiState.Success<ImmutableList<Race>>>(this)
                    assertEquals(racesTestData, data)
                }
            }
        }
    }
}
