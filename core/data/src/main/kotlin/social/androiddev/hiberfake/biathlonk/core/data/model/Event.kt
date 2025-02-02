package social.androiddev.hiberfake.biathlonk.core.data.model

import social.androiddev.hiberfake.biathlonk.core.common.toLocale
import social.androiddev.hiberfake.biathlonk.core.model.Event
import social.androiddev.hiberfake.biathlonk.core.network.model.NetworkEvent

internal fun NetworkEvent.asExternalModel() = Event(
    id = id,
    locale = iocCountryCode.toLocale(),
    description = description,
    from = from,
    to = to,
)
