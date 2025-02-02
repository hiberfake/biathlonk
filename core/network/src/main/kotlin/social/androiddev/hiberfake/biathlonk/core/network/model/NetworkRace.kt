package social.androiddev.hiberfake.biathlonk.core.network.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkRace(
    @SerialName("RaceId")
    val id: String,
    @SerialName("StartTime")
    val start: Instant,
    @SerialName("ShortDescription")
    val description: String,
)
