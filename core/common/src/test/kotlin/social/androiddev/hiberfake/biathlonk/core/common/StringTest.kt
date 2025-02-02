package social.androiddev.hiberfake.biathlonk.core.common

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.Locale

@RunWith(Parameterized::class)
class StringTest(
    private val iocCountryCode: String,
    private val expected: Locale,
) {
    @Test
    fun toLocale() {
        assert(iocCountryCode.toLocale() == expected)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "iocCountryCode={0}, expected={1}")
        fun data() = listOf(
            arrayOf("USA", Locale.Builder().setRegion("US").build()),
            arrayOf("GER", Locale.Builder().setRegion("DE").build()),
        )
    }
}
