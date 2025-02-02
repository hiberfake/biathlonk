package social.androiddev.hiberfake.biathlonk.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCup(
    @SerialName("CupId")
    val id: String,
    @SerialName("Level")
    val level: Int,
    @SerialName("CatId")
    val catId: String,
    @SerialName("DisciplineId")
    val disciplineId: String,
)
