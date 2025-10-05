package social.androiddev.hiberfake.biathlonk.core.common

import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.Locale
import kotlin.test.Test

@RunWith(Parameterized::class)
class LocaleTest(
    private val locale: Locale,
    private val expected: String,
) {
    @Test
    fun toEmoji() {
        assert(locale.toEmoji() == expected)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "locale={0}, expected={1}")
        fun data() = listOf(
            arrayOf(Locale.US, "🇺🇸"),
            arrayOf(Locale.GERMANY, "🇩🇪"),
        )
    }
}
