package social.androiddev.hiberfake.biathlonk.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCupResults(
    @SerialName("CupId")
    val cupId: String,
    @SerialName("CupName")
    val cupName: String,
    @SerialName("Rows")
    val rows: List<NetworkAthlete>,
)
