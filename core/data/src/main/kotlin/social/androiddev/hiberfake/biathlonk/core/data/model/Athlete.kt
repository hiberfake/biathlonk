package social.androiddev.hiberfake.biathlonk.core.data.model

import social.androiddev.hiberfake.biathlonk.core.model.Athlete
import social.androiddev.hiberfake.biathlonk.core.network.model.NetworkAthlete

internal fun NetworkAthlete.asExternalModel() = Athlete(
    id = id,
    familyName = familyName,
    givenName = givenName,
    iocCountryCode = iocCountryCode,
    rank = rank.toInt(),
    score = score.toInt(),
)
