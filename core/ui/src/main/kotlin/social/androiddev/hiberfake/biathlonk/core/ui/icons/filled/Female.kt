package social.androiddev.hiberfake.biathlonk.core.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import social.androiddev.hiberfake.biathlonk.core.ui.icons.Icons

val Icons.Filled.Female: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Filled.Female",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960f,
        viewportHeight = 960f,
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(440f, 760f)
            horizontalLineToRelative(-40f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            reflectiveQuadTo(360f, 720f)
            quadToRelative(0f, -17f, 11.5f, -28.5f)
            reflectiveQuadTo(400f, 680f)
            horizontalLineToRelative(40f)
            verticalLineToRelative(-84f)
            quadToRelative(-79f, -14f, -129.5f, -75.5f)
            reflectiveQuadTo(260f, 378f)
            quadToRelative(0f, -91f, 64.5f, -154.5f)
            reflectiveQuadTo(480f, 160f)
            quadToRelative(91f, 0f, 155.5f, 63.5f)
            reflectiveQuadTo(700f, 378f)
            quadToRelative(0f, 81f, -50.5f, 142.5f)
            reflectiveQuadTo(520f, 596f)
            verticalLineToRelative(84f)
            horizontalLineToRelative(40f)
            quadToRelative(17f, 0f, 28.5f, 11.5f)
            reflectiveQuadTo(600f, 720f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(560f, 760f)
            horizontalLineToRelative(-40f)
            verticalLineToRelative(40f)
            quadToRelative(0f, 17f, -11.5f, 28.5f)
            reflectiveQuadTo(480f, 840f)
            quadToRelative(-17f, 0f, -28.5f, -11.5f)
            reflectiveQuadTo(440f, 800f)
            verticalLineToRelative(-40f)
            close()
            moveTo(480f, 520f)
            quadToRelative(58f, 0f, 99f, -41f)
            reflectiveQuadToRelative(41f, -99f)
            quadToRelative(0f, -58f, -41f, -99f)
            reflectiveQuadToRelative(-99f, -41f)
            quadToRelative(-58f, 0f, -99f, 41f)
            reflectiveQuadToRelative(-41f, 99f)
            quadToRelative(0f, 58f, 41f, 99f)
            reflectiveQuadToRelative(99f, 41f)
            close()
        }
    }.build()
}
