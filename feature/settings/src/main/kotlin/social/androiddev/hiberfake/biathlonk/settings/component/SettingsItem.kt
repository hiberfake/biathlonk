package social.androiddev.hiberfake.biathlonk.settings.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import social.androiddev.hiberfake.biathlonk.core.ui.ListItemDefaults

@Composable
internal fun SettingsItem(
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeightIn(min = ListItemDefaults.OneLineListItemContainerHeight)
            .clickable(
                enabled = enabled,
                onClick = onClick,
            )
            .padding(ListItemDefaults.ContainerPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}
