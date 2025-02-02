package social.androiddev.hiberfake.biathlonk.core.data.repository

import com.skydoves.sandwich.ApiResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import social.androiddev.hiberfake.biathlonk.core.model.Season
import social.androiddev.hiberfake.biathlonk.core.network.BiathlonResultsRemoteDataSource
import social.androiddev.hiberfake.biathlonk.core.network.model.NetworkSeason
import java.io.IOException
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SeasonsRepositoryTest {
    val mockRemoteDataSource = mockk<BiathlonResultsRemoteDataSource>()
    val testScope = TestScope(UnconfinedTestDispatcher())

    val repository = SeasonsRepository(
        biathlonResultsRemoteDataSource = mockRemoteDataSource,
        externalScope = testScope,
    )

    @Test
    fun getSeasons_returnsListOfSeasons() = runTest {
        val networkSeasons = listOf(
            NetworkSeason(
                id = "2526",
                description = "2025/2026",
                isCurrentlyScheduled = true,
            ),
            NetworkSeason(
                id = "2425",
                description = "2024/2025",
                hasCurrentResults = true,
            ),
        )
        val seasons = persistentListOf(
            Season(
                id = "2526",
                description = "2025/2026",
                isCurrentlyScheduled = true,
            ),
            Season(
                id = "2425",
                description = "2024/2025",
                hasCurrentResults = true,
            ),
        )

        coEvery { mockRemoteDataSource.getSeasons() } returns ApiResponse.of { networkSeasons }

        val flow = repository.getSeasons()

        assertEquals(seasons, flow.first())
    }

    @Test
    fun getSeasons_returnsEmptyList_onError() = runTest {
        val seasons = persistentListOf<Season>()

        coEvery { mockRemoteDataSource.getSeasons() } returns ApiResponse.Failure.Error("")

        val flow = repository.getSeasons()

        assertEquals(seasons, flow.first())
    }

    @Test
    fun getSeasons_returnsEmptyList_onException() = runTest {
        val seasons = persistentListOf<Season>()

        coEvery { mockRemoteDataSource.getSeasons() } returns ApiResponse.exception(IOException())

        val flow = repository.getSeasons()

        assertEquals(seasons, flow.first())
    }
}
