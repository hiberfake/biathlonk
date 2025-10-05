package social.androiddev.hiberfake.biathlonk.core.data.repository

import com.skydoves.sandwich.getOrElse
import com.skydoves.sandwich.mapSuccess
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import social.androiddev.hiberfake.biathlonk.core.common.di.ApplicationScope
import social.androiddev.hiberfake.biathlonk.core.data.model.asExternalModel
import social.androiddev.hiberfake.biathlonk.core.model.Season
import social.androiddev.hiberfake.biathlonk.core.network.BiathlonResultsRemoteDataSource
import social.androiddev.hiberfake.biathlonk.core.network.model.NetworkSeason
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SeasonsRepository @Inject constructor(
    @ApplicationScope private val externalScope: CoroutineScope,
    private val biathlonResultsRemoteDataSource: BiathlonResultsRemoteDataSource,
) {
    private val mutex = Mutex()

    private var latestSeasons: List<Season> = emptyList()

    /**
     * Stream API to fetch all the seasons.
     *
     * The seasons are cached in memory.
     *
     * @param refresh [Boolean] Whether to refresh the seasons from the remote data source.
     *
     * @return [Flow] of [ImmutableList] of [Season]
     */
    fun getSeasonsStream(refresh: Boolean = false) = flow {
        val seasons = if (refresh || latestSeasons.isEmpty()) {
            externalScope.async {
                biathlonResultsRemoteDataSource.getSeasons()
                    .mapSuccess { map(NetworkSeason::asExternalModel) }
                    .suspendOnSuccess {
                        mutex.withLock {
                            latestSeasons = data
                        }
                    }
                    .getOrElse(emptyList())
            }.await()
        } else {
            mutex.withLock { latestSeasons }
        }

        emit(seasons.toImmutableList())
    }
}
