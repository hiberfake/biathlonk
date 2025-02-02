package social.androiddev.hiberfake.biathlonk.core.network

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.getOrThrow
import com.skydoves.sandwich.isError
import com.skydoves.sandwich.isException
import com.skydoves.sandwich.isFailure
import com.skydoves.sandwich.isSuccess
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import social.androiddev.hiberfake.biathlonk.core.network.api.BiathlonResultsApi
import social.androiddev.hiberfake.biathlonk.core.network.model.NetworkSeason
import java.io.IOException
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BiathlonResultsRemoteDataSourceTest {
    val mockApi = mockk<BiathlonResultsApi>()

    val dataSource = BiathlonResultsRemoteDataSource(
        ioDispatcher = UnconfinedTestDispatcher(),
        biathlonResultsApi = mockApi,
    )

    @Test
    fun getSeasons_returnsSuccess() = runTest {
        val seasons = listOf(
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

        coEvery { mockApi.getSeasons() } returns ApiResponse.of { seasons }

        val response = dataSource.getSeasons()

        assert(response.isSuccess)
        assertEquals(seasons, response.getOrThrow())
    }

    @Test
    fun getSeasons_returnsError() = runTest {
        coEvery { mockApi.getSeasons() } returns ApiResponse.Failure.Error(Unit)

        val response = dataSource.getSeasons()

        assert(response.isFailure)
        assert(response.isError)
    }

    @Test
    fun getSeasons_returnsException() = runTest {
        coEvery { mockApi.getSeasons() } returns ApiResponse.exception(IOException())

        val response = dataSource.getSeasons()

        assert(response.isFailure)
        assert(response.isException)
    }
}
