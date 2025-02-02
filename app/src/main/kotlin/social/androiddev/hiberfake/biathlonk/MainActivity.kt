package social.androiddev.hiberfake.biathlonk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import social.androiddev.hiberfake.biathlonk.core.designsystem.theme.BiathlonTheme
import social.androiddev.hiberfake.biathlonk.ui.BiathlonApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        setContent {
            BiathlonTheme {
                BiathlonApp()
            }
        }
    }
}
