package social.androiddev.hiberfake.biathlonk.core.data.repository

import com.skydoves.sandwich.getOrElse
import com.skydoves.sandwich.mapSuccess
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import social.androiddev.hiberfake.biathlonk.core.common.di.ApplicationScope
import social.androiddev.hiberfake.biathlonk.core.data.model.asExternalModel
import social.androiddev.hiberfake.biathlonk.core.model.Category
import social.androiddev.hiberfake.biathlonk.core.model.CupResults
import social.androiddev.hiberfake.biathlonk.core.network.BiathlonResultsRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResultsRepository @Inject constructor(
    private val biathlonResultsRemoteDataSource: BiathlonResultsRemoteDataSource,
    @ApplicationScope private val externalScope: CoroutineScope,
) {
    private val mutex = Mutex()

    private var latestCupResultsBySeasonId: MutableMap<String, List<CupResults>> = mutableMapOf()

    // TODO: Discipline (TS — Total Score)
    fun getCupResults(
        seasonId: String,
        refresh: Boolean = false,
    ) = flow {
        val results = if (refresh || latestCupResultsBySeasonId[seasonId].isNullOrEmpty()) {
            externalScope.async {
                val cups = biathlonResultsRemoteDataSource.getCups(seasonId)
                    .mapSuccess {
                        this
                            .filter { it.level == 1 }
                            .filter { it.catId in Category.entries.map(Category::name) }
                            .filter { it.disciplineId == "TS" }
                    }
                    .getOrElse(emptyList())

                val cupResults = cups.map { cup ->
                    async {
                        biathlonResultsRemoteDataSource.getCupResults(cup.id)
                            .mapSuccess { asExternalModel(Category.valueOf(cup.catId)) }
                            .getOrElse(null)
                    }
                }

                cupResults.awaitAll()
                    .filterNotNull()
                    .also { cupResults ->
                        mutex.withLock {
                            latestCupResultsBySeasonId += seasonId to cupResults
                        }
                    }
            }.await()
        } else {
            mutex.withLock { latestCupResultsBySeasonId[seasonId].orEmpty() }
        }

        emit(results.toImmutableList())
    }
}
