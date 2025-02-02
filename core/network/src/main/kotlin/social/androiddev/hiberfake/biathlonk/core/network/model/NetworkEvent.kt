package social.androiddev.hiberfake.biathlonk.core.network.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkEvent(
    @SerialName("EventId")
    val id: String,
    @SerialName("Nat")
    val iocCountryCode: String,
    @SerialName("ShortDescription")
    val description: String,
    @SerialName("StartDate")
    val from: Instant,
    @SerialName("EndDate")
    val to: Instant,
)
