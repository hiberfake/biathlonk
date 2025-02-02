package social.androiddev.hiberfake.biathlonk.core.ui.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
infix fun PaddingValues.plus(all: Dp) = plus(
    start = all,
    top = all,
    end = all,
    bottom = all,
)

@Composable
fun PaddingValues.plus(
    horizontal: Dp = 0.dp,
    vertical: Dp = 0.dp,
) = plus(
    start = horizontal,
    top = vertical,
    end = horizontal,
    bottom = vertical,
)

@Composable
fun PaddingValues.plus(
    start: Dp = 0.dp,
    top: Dp = 0.dp,
    end: Dp = 0.dp,
    bottom: Dp = 0.dp,
): PaddingValues {
    val layoutDirection = LocalLayoutDirection.current

    return PaddingValues(
        start = calculateStartPadding(layoutDirection).plus(start),
        top = calculateTopPadding().plus(top),
        end = calculateEndPadding(layoutDirection).plus(end),
        bottom = calculateBottomPadding().plus(bottom),
    )
}
