package social.androiddev.hiberfake.biathlonk.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkSeason(
    @SerialName("SeasonId")
    val id: String,
    @SerialName("Description")
    val description: String,
    @SerialName("IsCurrentScheduled")
    val isCurrentlyScheduled: Boolean = false,
    @SerialName("IsCurrentResults")
    val hasCurrentResults: Boolean = false,
)
