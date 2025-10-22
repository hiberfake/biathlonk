package social.androiddev.hiberfake.biathlonk.core.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import social.androiddev.hiberfake.biathlonk.core.ui.icons.Icons

val Icons.Filled.Leaderboard: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Filled.Leaderboard",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960f,
        viewportHeight = 960f,
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(120f, 840f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            reflectiveQuadTo(80f, 800f)
            verticalLineToRelative(-400f)
            quadToRelative(0f, -17f, 11.5f, -28.5f)
            reflectiveQuadTo(120f, 360f)
            horizontalLineToRelative(140f)
            quadToRelative(17f, 0f, 28.5f, 11.5f)
            reflectiveQuadTo(300f, 400f)
            verticalLineToRelative(400f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(260f, 840f)
            lineTo(120f, 840f)
            close()
            moveTo(410f, 840f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            reflectiveQuadTo(370f, 800f)
            verticalLineToRelative(-640f)
            quadToRelative(0f, -17f, 11.5f, -28.5f)
            reflectiveQuadTo(410f, 120f)
            horizontalLineToRelative(140f)
            quadToRelative(17f, 0f, 28.5f, 11.5f)
            reflectiveQuadTo(590f, 160f)
            verticalLineToRelative(640f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(550f, 840f)
            lineTo(410f, 840f)
            close()
            moveTo(700f, 840f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            reflectiveQuadTo(660f, 800f)
            verticalLineToRelative(-320f)
            quadToRelative(0f, -17f, 11.5f, -28.5f)
            reflectiveQuadTo(700f, 440f)
            horizontalLineToRelative(140f)
            quadToRelative(17f, 0f, 28.5f, 11.5f)
            reflectiveQuadTo(880f, 480f)
            verticalLineToRelative(320f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(840f, 840f)
            lineTo(700f, 840f)
            close()
        }
    }.build()
}
