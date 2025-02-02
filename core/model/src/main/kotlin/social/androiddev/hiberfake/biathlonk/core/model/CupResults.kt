package social.androiddev.hiberfake.biathlonk.core.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class CupResults(
    val cupId: String,
    val cupName: String,
    val category: Category,
    val rows: ImmutableList<Athlete>,
) {
    companion object {
        val preview = CupResults(
            cupId = "BT2425SWRLCP__SWTS",
            cupName = "Women's World Cup Total Score",
            category = Category.SW,
            rows = persistentListOf(Athlete.preview),
        )
    }
}
