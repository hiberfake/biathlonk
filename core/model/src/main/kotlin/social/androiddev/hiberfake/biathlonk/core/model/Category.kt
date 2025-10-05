package social.androiddev.hiberfake.biathlonk.core.model

enum class Category(val id: String) {
    SINGLE_WOMEN("SW"),
    SINGLE_MEN("SM"),
    ;

    companion object {
        fun ofId(id: String) = when (id) {
            "SW" -> SINGLE_WOMEN
            "SM" -> SINGLE_MEN
            else -> error("Invalid category ID: $id")
        }
    }
}
