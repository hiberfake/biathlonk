package social.androiddev.hiberfake.biathlonk.settings.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import social.androiddev.hiberfake.biathlonk.core.designsystem.theme.BiathlonTheme
import social.androiddev.hiberfake.biathlonk.core.ui.BiathlonListItemDefaults

@Composable
internal fun SettingsItem(
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    trailingContent: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeightIn(min = BiathlonListItemDefaults.OneLineListItemContainerHeight)
            .clickable(
                enabled = enabled,
                onClick = onClick,
            )
            .padding(BiathlonListItemDefaults.ContainerPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            modifier = Modifier,
            style = MaterialTheme.typography.titleMedium,
        )

        trailingContent?.let {
            Spacer(Modifier.weight(weight = 1f))
            Spacer(Modifier.width(16.dp))
            trailingContent()
        }
    }
}

@Preview
@Composable
internal fun SettingsItemPreview() = BiathlonTheme {
    Surface {
        SettingsItem(label = "Open-Source-Lizenzen")
    }
}

@Preview
@Composable
internal fun SettingsItemWithTrailingContentPreview() = BiathlonTheme {
    Surface {
        SettingsItem(label = "Version") {
            Text(
                text = "1.0.0 (1)",
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}
