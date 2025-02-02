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
import social.androiddev.hiberfake.biathlonk.core.model.Event
import social.androiddev.hiberfake.biathlonk.core.model.Race
import social.androiddev.hiberfake.biathlonk.core.network.BiathlonResultsRemoteDataSource
import social.androiddev.hiberfake.biathlonk.core.network.model.NetworkEvent
import social.androiddev.hiberfake.biathlonk.core.network.model.NetworkRace
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventsRepository @Inject constructor(
    private val biathlonResultsRemoteDataSource: BiathlonResultsRemoteDataSource,
    @ApplicationScope private val externalScope: CoroutineScope,
) {
    private val mutex = Mutex()

    private var latestEventsBySeasonId: MutableMap<String, List<Event>> = mutableMapOf()

    /**
     * One-shot API to fetch all the events for a given season.
     *
     * The events are cached in memory.
     *
     * @param seasonId [String] The id of the season to fetch events for.
     *
     * @return [ImmutableList] of [Event]
     */
    suspend fun getEvents(seasonId: String): ImmutableList<Event> {
        val events = if (latestEventsBySeasonId[seasonId].isNullOrEmpty()) {
            biathlonResultsRemoteDataSource.getEvents(seasonId)
                .mapSuccess { map(NetworkEvent::asExternalModel) }
                .suspendOnSuccess {
                    mutex.withLock {
                        latestEventsBySeasonId += seasonId to data
                    }
                }
                .getOrElse(emptyList())
        } else {
            mutex.withLock { latestEventsBySeasonId[seasonId].orEmpty() }
        }

        return events.toImmutableList()
    }

    /**
     * Stream API to fetch all the events for a given season.
     *
     * The events are cached in memory.
     *
     * @param seasonId [String] The id of the season to fetch events for.
     *
     * @return [Flow] of [ImmutableList] of [Event]
     */
    fun getEventsStream(seasonId: String) = flow {
        val events = if (latestEventsBySeasonId[seasonId].isNullOrEmpty()) {
            externalScope.async {
                biathlonResultsRemoteDataSource.getEvents(seasonId)
                    .mapSuccess { map(NetworkEvent::asExternalModel) }
                    .suspendOnSuccess {
                        mutex.withLock {
                            latestEventsBySeasonId += seasonId to data
                        }
                    }
                    .getOrElse(emptyList())
            }.await()
        } else {
            mutex.withLock { latestEventsBySeasonId[seasonId].orEmpty() }
        }

        emit(events.toImmutableList())
    }

    /**
     * One-shot API to fetch all the races for a given event.
     *
     * @param eventId [String] The id of the event to fetch races for.
     *
     * @return [ImmutableList] of [Race]
     */
    suspend fun getRaces(eventId: String): ImmutableList<Race> {
        val races = biathlonResultsRemoteDataSource.getRaces(eventId)
            .mapSuccess { map(NetworkRace::asExternalModel) }
            .getOrElse(emptyList())

        return races.toImmutableList()
    }
}
