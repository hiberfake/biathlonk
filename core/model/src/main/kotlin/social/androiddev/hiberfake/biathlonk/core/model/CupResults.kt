package social.androiddev.hiberfake.biathlonk.core.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class CupResults(
    val id: String,
    val name: String,
    val category: Category,
    val athletes: ImmutableList<Athlete>,
) {
    companion object {
        val preview = CupResults(
            id = "BT2425SWRLCP__SWTS",
            name = "Women's World Cup Total Score",
            category = Category.SINGLE_WOMEN,
            athletes = persistentListOf(Athlete.preview),
        )
    }
}
