package social.androiddev.hiberfake.biathlonk.core.domain.usecase

import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.transformLatest
import social.androiddev.hiberfake.biathlonk.core.data.repository.EventsRepository
import social.androiddev.hiberfake.biathlonk.core.data.repository.SeasonsRepository
import social.androiddev.hiberfake.biathlonk.core.model.Season
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val seasonsRepository: SeasonsRepository,
    private val eventsRepository: EventsRepository,
) {
    operator fun invoke() = seasonsRepository.getSeasonsStream()
        .mapNotNull { seasons ->
            seasons.find(Season::isCurrentlyScheduled)
        }
        .transformLatest { season ->
            emitAll(eventsRepository.getEventsStream(season.id))
        }
}
