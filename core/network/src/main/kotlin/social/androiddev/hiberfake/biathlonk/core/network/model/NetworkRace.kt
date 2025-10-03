package social.androiddev.hiberfake.biathlonk.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class NetworkRace(
    @SerialName("RaceId")
    val id: String,
    @SerialName("StartTime")
    val start: Instant,
    @SerialName("ShortDescription")
    val description: String,
)
