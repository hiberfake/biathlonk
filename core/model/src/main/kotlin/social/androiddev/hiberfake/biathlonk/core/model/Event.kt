package social.androiddev.hiberfake.biathlonk.core.model

import android.content.Context
import android.text.format.DateUtils
import android.text.format.DateUtils.FORMAT_NO_YEAR
import android.text.format.DateUtils.FORMAT_SHOW_DATE
import android.text.format.DateUtils.FORMAT_SHOW_YEAR
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import java.util.Locale

data class Event(
    val id: String,
    val locale: Locale,
    val description: String,
    val from: Instant,
    val to: Instant,
) {
    companion object {
        val preview = Event(
            id = "BT2526SWRLCP01",
            locale = Locale.Builder().setRegion("SE").build(),
            description = "Östersund",
            from = Instant.parse(input = "2025-11-29T12:00:00Z"),
            to = Instant.parse(input = "2025-12-07T12:00:00Z"),
        )
    }
}

fun Event.formatDateRange(context: Context): String {
    val timeZone = TimeZone.currentSystemDefault()
    val showYear = Clock.System.todayIn(timeZone).year != to.toLocalDateTime(timeZone).date.year

    return DateUtils.formatDateRange(
        context,
        from.toEpochMilliseconds(),
        to.toEpochMilliseconds(),
        if (showYear) FORMAT_SHOW_YEAR else FORMAT_NO_YEAR or FORMAT_SHOW_DATE,
    )
}
