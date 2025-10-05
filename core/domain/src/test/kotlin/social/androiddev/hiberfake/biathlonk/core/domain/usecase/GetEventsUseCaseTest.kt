package social.androiddev.hiberfake.biathlonk.core.domain.usecase

import app.cash.turbine.test
import io.mockk.every
import io.mockk.mockk
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import social.androiddev.hiberfake.biathlonk.core.data.repository.EventsRepository
import social.androiddev.hiberfake.biathlonk.core.data.repository.SeasonsRepository
import social.androiddev.hiberfake.biathlonk.core.testing.eventsTestData
import social.androiddev.hiberfake.biathlonk.core.testing.seasonsTestData
import kotlin.test.Test
import kotlin.test.assertTrue

class GetEventsUseCaseTest {
    val mockSeasonsRepository = mockk<SeasonsRepository>()
    val mockEventsRepository = mockk<EventsRepository>()

    val useCase: GetEventsUseCase = GetEventsUseCase(
        seasonsRepository = mockSeasonsRepository,
        eventsRepository = mockEventsRepository,
    )

    @Test
    fun emitsEmptyListOfScheduledEvents() = runTest {
        every { mockSeasonsRepository.getSeasonsStream() } returns flowOf(seasonsTestData)
        every { mockEventsRepository.getEventsStream(any()) } returns flowOf(persistentListOf())

        useCase().test {
            assertTrue { awaitItem().isEmpty() }
            awaitComplete()
        }
    }

    @Test
    fun emitsListOfScheduledEvents() = runTest {
        every { mockSeasonsRepository.getSeasonsStream() } returns flowOf(seasonsTestData)
        every { mockEventsRepository.getEventsStream(any()) } returns flowOf(eventsTestData)

        useCase().test {
            assertTrue { awaitItem().isNotEmpty() }
            awaitComplete()
        }
    }
}
