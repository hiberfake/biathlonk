package social.androiddev.hiberfake.biathlonk.core.domain.usecase

import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.transformLatest
import social.androiddev.hiberfake.biathlonk.core.data.repository.ResultsRepository
import social.androiddev.hiberfake.biathlonk.core.data.repository.SeasonsRepository
import social.androiddev.hiberfake.biathlonk.core.model.Season
import javax.inject.Inject

class GetCupResultsUseCase @Inject constructor(
    private val seasonsRepository: SeasonsRepository,
    private val resultsRepository: ResultsRepository,
) {
    operator fun invoke() = seasonsRepository.getSeasonsStream()
        .mapNotNull { seasons ->
            seasons.find(Season::hasCurrentResults)
        }
        .transformLatest { season ->
            emitAll(resultsRepository.getCupResultsStream(season.id))
        }
}
