package social.androiddev.hiberfake.biathlonk.core.data.model

import social.androiddev.hiberfake.biathlonk.core.model.Race
import social.androiddev.hiberfake.biathlonk.core.network.model.NetworkRace

internal fun NetworkRace.asExternalModel() = Race(
    id = id,
    start = start,
    description = description,
)
