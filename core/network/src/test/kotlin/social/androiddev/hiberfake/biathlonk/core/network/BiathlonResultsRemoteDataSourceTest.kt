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
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import social.androiddev.hiberfake.biathlonk.core.network.api.BiathlonResultsApi
import social.androiddev.hiberfake.biathlonk.core.testing.MainDispatcherRule
import social.androiddev.hiberfake.biathlonk.core.testing.networkSeasonsTestData
import java.io.IOException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class BiathlonResultsRemoteDataSourceTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    val mockApi = mockk<BiathlonResultsApi>()

    val dataSource = BiathlonResultsRemoteDataSource(
        dispatcher = mainDispatcherRule.testDispatcher,
        biathlonResultsApi = mockApi,
    )

    @Test
    fun getSeasons_returnsSuccess() = runTest {
        coEvery { mockApi.getSeasons() } returns ApiResponse.of { networkSeasonsTestData }

        val response = dataSource.getSeasons()

        assertTrue { response.isSuccess }
        assertEquals(networkSeasonsTestData, response.getOrThrow())
    }

    @Test
    fun getSeasons_returnsError() = runTest {
        coEvery { mockApi.getSeasons() } returns ApiResponse.Failure.Error(Unit)

        val response = dataSource.getSeasons()

        assertTrue { response.isFailure }
        assertTrue { response.isError }
    }

    @Test
    fun getSeasons_returnsException() = runTest {
        coEvery { mockApi.getSeasons() } returns ApiResponse.exception(IOException())

        val response = dataSource.getSeasons()

        assertTrue { response.isFailure }
        assertTrue { response.isException }
    }
}
