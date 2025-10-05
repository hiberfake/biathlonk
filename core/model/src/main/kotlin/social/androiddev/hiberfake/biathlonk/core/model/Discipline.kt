package social.androiddev.hiberfake.biathlonk.core.model

enum class Discipline(val id: String) {
    TOTAL_SCORE("TS"),
    ;

    companion object {
        fun ofId(id: String) = when (id) {
            "TS" -> TOTAL_SCORE
            else -> error("Invalid discipline ID: $id")
        }
    }
}
