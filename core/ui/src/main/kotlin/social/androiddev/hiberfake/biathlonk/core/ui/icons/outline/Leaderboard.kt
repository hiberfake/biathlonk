package social.androiddev.hiberfake.biathlonk.core.ui.icons.outline

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import social.androiddev.hiberfake.biathlonk.core.ui.icons.Icons

val Icons.Outlined.Leaderboard: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Outline.Leaderboard",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960f,
        viewportHeight = 960f,
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(160f, 760f)
            horizontalLineToRelative(160f)
            verticalLineToRelative(-320f)
            lineTo(160f, 440f)
            verticalLineToRelative(320f)
            close()
            moveTo(400f, 760f)
            horizontalLineToRelative(160f)
            verticalLineToRelative(-560f)
            lineTo(400f, 200f)
            verticalLineToRelative(560f)
            close()
            moveTo(640f, 760f)
            horizontalLineToRelative(160f)
            verticalLineToRelative(-240f)
            lineTo(640f, 520f)
            verticalLineToRelative(240f)
            close()
            moveTo(80f, 760f)
            verticalLineToRelative(-320f)
            quadToRelative(0f, -33f, 23.5f, -56.5f)
            reflectiveQuadTo(160f, 360f)
            horizontalLineToRelative(160f)
            verticalLineToRelative(-160f)
            quadToRelative(0f, -33f, 23.5f, -56.5f)
            reflectiveQuadTo(400f, 120f)
            horizontalLineToRelative(160f)
            quadToRelative(33f, 0f, 56.5f, 23.5f)
            reflectiveQuadTo(640f, 200f)
            verticalLineToRelative(240f)
            horizontalLineToRelative(160f)
            quadToRelative(33f, 0f, 56.5f, 23.5f)
            reflectiveQuadTo(880f, 520f)
            verticalLineToRelative(240f)
            quadToRelative(0f, 33f, -23.5f, 56.5f)
            reflectiveQuadTo(800f, 840f)
            lineTo(160f, 840f)
            quadToRelative(-33f, 0f, -56.5f, -23.5f)
            reflectiveQuadTo(80f, 760f)
            close()
        }
    }.build()
}
