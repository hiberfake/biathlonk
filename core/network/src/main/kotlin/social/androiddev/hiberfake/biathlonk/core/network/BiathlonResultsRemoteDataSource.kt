package social.androiddev.hiberfake.biathlonk.core.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import social.androiddev.hiberfake.biathlonk.core.common.di.BiathlonResultsDispatchers.IO
import social.androiddev.hiberfake.biathlonk.core.common.di.Dispatcher
import social.androiddev.hiberfake.biathlonk.core.network.api.BiathlonResultsApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BiathlonResultsRemoteDataSource @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val biathlonResultsApi: BiathlonResultsApi,
) {
    suspend fun getSeasons() = withContext(ioDispatcher) {
        biathlonResultsApi.getSeasons()
    }

    suspend fun getEvents(seasonId: String) = withContext(ioDispatcher) {
        biathlonResultsApi.getEvents(seasonId = seasonId)
    }

    suspend fun getRaces(eventId: String) = withContext(ioDispatcher) {
        biathlonResultsApi.getRaces(eventId = eventId)
    }

//    fun getRaces(eventId: String) = flow {
//        withContext(ioDispatcher) {
//            emit(biathlonResultsApi.getRaces(eventId = eventId))
//        }
//    }

    suspend fun getCups(seasonId: String) = withContext(ioDispatcher) {
        biathlonResultsApi.getCups(seasonId = seasonId)
    }

    suspend fun getCupResults(cupId: String) = withContext(ioDispatcher) {
        biathlonResultsApi.getCupResults(cupId = cupId)
    }
}
