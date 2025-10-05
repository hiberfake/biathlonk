package social.androiddev.hiberfake.biathlonk.core.data.repository

import app.cash.turbine.test
import com.skydoves.sandwich.ApiResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import social.androiddev.hiberfake.biathlonk.core.network.BiathlonResultsRemoteDataSource
import social.androiddev.hiberfake.biathlonk.core.testing.MainDispatcherRule
import social.androiddev.hiberfake.biathlonk.core.testing.networkSeasonsTestData
import social.androiddev.hiberfake.biathlonk.core.testing.seasonsTestData
import java.io.IOException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SeasonsRepositoryTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    val mockRemoteDataSource = mockk<BiathlonResultsRemoteDataSource>()

    val repository = SeasonsRepository(
        externalScope = TestScope(mainDispatcherRule.testDispatcher),
        biathlonResultsRemoteDataSource = mockRemoteDataSource,
    )

    @Test
    fun getSeasonsStream_emitsListOfSeasons() = runTest {
        coEvery { mockRemoteDataSource.getSeasons() } returns ApiResponse.of { networkSeasonsTestData }

        repository.getSeasonsStream().test {
            assertEquals(seasonsTestData, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun getSeasonsStream_emitsEmptyList_onError() = runTest {
        coEvery { mockRemoteDataSource.getSeasons() } returns ApiResponse.Failure.Error("")

        repository.getSeasonsStream().test {
            assertTrue { awaitItem().isEmpty() }
            awaitComplete()
        }
    }

    @Test
    fun getSeasonsStream_emitsEmptyList_onException() = runTest {
        coEvery { mockRemoteDataSource.getSeasons() } returns ApiResponse.exception(IOException())

        repository.getSeasonsStream().test {
            assertTrue { awaitItem().isEmpty() }
            awaitComplete()
        }
    }
}
