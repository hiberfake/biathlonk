package social.androiddev.hiberfake.biathlonk.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCupResults(
    @SerialName("CupId")
    val id: String,
    @SerialName("CupName")
    val name: String,
    @SerialName("Rows")
    val athletes: List<NetworkAthlete>,
)
