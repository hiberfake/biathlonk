package social.androiddev.hiberfake.biathlonk.core.model

import kotlin.time.Instant

data class Race(
    val id: String,
    val start: Instant,
    val description: String,
) {
    companion object {
        val preview = Race(
            id = "BT2526SWRLCP01SWRL",
            start = Instant.parse(input = "2025-11-29T12:30:00Z"),
            description = "4x6 km Staffel Frauen",
        )
    }
}
