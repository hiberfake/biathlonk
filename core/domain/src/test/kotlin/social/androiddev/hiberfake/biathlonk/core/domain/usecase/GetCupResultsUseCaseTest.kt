package social.androiddev.hiberfake.biathlonk.core.domain.usecase

import app.cash.turbine.test
import io.mockk.every
import io.mockk.mockk
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import social.androiddev.hiberfake.biathlonk.core.data.repository.ResultsRepository
import social.androiddev.hiberfake.biathlonk.core.data.repository.SeasonsRepository
import social.androiddev.hiberfake.biathlonk.core.testing.cupResultsTestData
import social.androiddev.hiberfake.biathlonk.core.testing.seasonsTestData
import kotlin.test.Test
import kotlin.test.assertTrue

class GetCupResultsUseCaseTest {
    private val mockSeasonsRepository = mockk<SeasonsRepository>()
    private val mockResultsRepository = mockk<ResultsRepository>()

    private val useCase = GetCupResultsUseCase(
        seasonsRepository = mockSeasonsRepository,
        resultsRepository = mockResultsRepository,
    )

    @Test
    fun emitsEmptyListOfCupResults() = runTest {
        every { mockSeasonsRepository.getSeasonsStream() } returns flowOf(seasonsTestData)
        every { mockResultsRepository.getCupResultsStream(any()) } returns flowOf(persistentListOf())

        useCase().test {
            assertTrue { awaitItem().isEmpty() }
            awaitComplete()
        }
    }

    @Test
    fun emitsListOfCupResults() = runTest {
        every { mockSeasonsRepository.getSeasonsStream() } returns flowOf(seasonsTestData)
        every { mockResultsRepository.getCupResultsStream(any()) } returns flowOf(cupResultsTestData)

        useCase().test {
            assertTrue { awaitItem().isNotEmpty() }
            awaitComplete()
        }
    }
}
