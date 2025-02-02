package social.androiddev.hiberfake.biathlonk.core.common

import java.text.NumberFormat

fun Number.format() = NumberFormat.getInstance().format(this)
