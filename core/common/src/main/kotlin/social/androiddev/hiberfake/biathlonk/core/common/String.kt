package social.androiddev.hiberfake.biathlonk.core.common

import java.util.Locale

fun String.toLocale(): Locale = localesByIocCountryCode.getOrElse(this) { Locale.ROOT }
