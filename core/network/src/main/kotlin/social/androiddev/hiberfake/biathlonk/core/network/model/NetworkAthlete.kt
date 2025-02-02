package social.androiddev.hiberfake.biathlonk.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkAthlete(
    @SerialName("IBUId")
    val id: String,
    @SerialName("FamilyName")
    val familyName: String,
    @SerialName("GivenName")
    val givenName: String,
    @SerialName("Nat")
    val iocCountryCode: String,
    @SerialName("Rank")
    val rank: String,
    @SerialName("Score")
    val score: String,
)
