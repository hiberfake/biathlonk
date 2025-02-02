package social.androiddev.hiberfake.biathlonk.core.data.model

import kotlinx.collections.immutable.toImmutableList
import social.androiddev.hiberfake.biathlonk.core.model.Category
import social.androiddev.hiberfake.biathlonk.core.model.CupResults
import social.androiddev.hiberfake.biathlonk.core.network.model.NetworkAthlete
import social.androiddev.hiberfake.biathlonk.core.network.model.NetworkCupResults

internal fun NetworkCupResults.asExternalModel(category: Category) = CupResults(
    cupId = cupId,
    cupName = cupName,
    category = category,
    rows = rows.map(NetworkAthlete::asExternalModel).toImmutableList(),
)
