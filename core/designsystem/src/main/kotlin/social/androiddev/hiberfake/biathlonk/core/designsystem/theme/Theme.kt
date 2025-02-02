package social.androiddev.hiberfake.biathlonk.core.designsystem.theme

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme()
private val DarkColorScheme = darkColorScheme()

@Composable
fun BiathlonTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    supportsDynamicColor: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        supportsDynamicColor -> {
            @SuppressLint("NewApi")
            if (isDarkTheme) {
                dynamicDarkColorScheme(LocalContext.current)
            } else {
                dynamicLightColorScheme(LocalContext.current)
            }
        }

        isDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}
