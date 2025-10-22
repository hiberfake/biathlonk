package social.androiddev.hiberfake.biathlonk.core.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import social.androiddev.hiberfake.biathlonk.core.ui.icons.Icons

val Icons.Filled.Male: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Filled.Male",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960f,
        viewportHeight = 960f,
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(800f, 200f)
            verticalLineToRelative(160f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(760f, 400f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            reflectiveQuadTo(720f, 360f)
            verticalLineToRelative(-63f)
            lineTo(561f, 455f)
            quadToRelative(19f, 28f, 29f, 59.5f)
            reflectiveQuadToRelative(10f, 65.5f)
            quadToRelative(0f, 92f, -64f, 156f)
            reflectiveQuadToRelative(-156f, 64f)
            quadToRelative(-92f, 0f, -156f, -64f)
            reflectiveQuadToRelative(-64f, -156f)
            quadToRelative(0f, -92f, 64f, -156f)
            reflectiveQuadToRelative(156f, -64f)
            quadToRelative(33f, 0f, 65f, 9.5f)
            reflectiveQuadToRelative(59f, 29.5f)
            lineToRelative(159f, -159f)
            horizontalLineToRelative(-63f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            reflectiveQuadTo(560f, 200f)
            quadToRelative(0f, -17f, 11.5f, -28.5f)
            reflectiveQuadTo(600f, 160f)
            horizontalLineToRelative(160f)
            quadToRelative(17f, 0f, 28.5f, 11.5f)
            reflectiveQuadTo(800f, 200f)
            close()
            moveTo(380f, 440f)
            quadToRelative(-58f, 0f, -99f, 41f)
            reflectiveQuadToRelative(-41f, 99f)
            quadToRelative(0f, 58f, 41f, 99f)
            reflectiveQuadToRelative(99f, 41f)
            quadToRelative(58f, 0f, 99f, -41f)
            reflectiveQuadToRelative(41f, -99f)
            quadToRelative(0f, -58f, -41f, -99f)
            reflectiveQuadToRelative(-99f, -41f)
            close()
        }
    }.build()
}
