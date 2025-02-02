package social.androiddev.hiberfake.biathlonk.core.data.model

import social.androiddev.hiberfake.biathlonk.core.model.Season
import social.androiddev.hiberfake.biathlonk.core.network.model.NetworkSeason

internal fun NetworkSeason.asExternalModel() = Season(
    id = id,
    description = description,
    isCurrentlyScheduled = isCurrentlyScheduled,
    hasCurrentResults = hasCurrentResults,
)
