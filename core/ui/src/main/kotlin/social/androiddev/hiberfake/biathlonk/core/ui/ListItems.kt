package social.androiddev.hiberfake.biathlonk.core.ui

import android.text.format.DateUtils.FORMAT_ABBREV_ALL
import android.text.format.DateUtils.FORMAT_SHOW_DATE
import android.text.format.DateUtils.FORMAT_SHOW_TIME
import android.text.format.DateUtils.FORMAT_SHOW_WEEKDAY
import android.text.format.DateUtils.formatDateTime
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import social.androiddev.hiberfake.biathlonk.core.common.format
import social.androiddev.hiberfake.biathlonk.core.common.toEmoji
import social.androiddev.hiberfake.biathlonk.core.common.toLocale
import social.androiddev.hiberfake.biathlonk.core.designsystem.theme.BiathlonTheme
import social.androiddev.hiberfake.biathlonk.core.model.Athlete
import social.androiddev.hiberfake.biathlonk.core.model.Event
import social.androiddev.hiberfake.biathlonk.core.model.Race
import social.androiddev.hiberfake.biathlonk.core.model.formatDateRange

@Composable
fun EventItem(
    event: Event,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .semantics(mergeDescendants = true) {}
            .fillMaxWidth()
            .requiredHeightIn(min = BiathlonListItemDefaults.TwoLinesListItemContainerHeight)
            .clickable(onClick = onClick)
            .padding(BiathlonListItemDefaults.ContainerPadding),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = buildAnnotatedString {
                append(event.description)
                withStyle(MaterialTheme.typography.bodyMedium.copy().toSpanStyle()) {
                    append(", ")
                    append(event.locale.displayCountry)
                    append(" ")
                    append(event.locale.toEmoji())
                }
            },
            modifier = Modifier.semantics {
                contentDescription = "${event.description}, ${event.locale.displayCountry}"
            },
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = remember { event.formatDateRange(context) },
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
fun RaceItem(
    race: Race,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .semantics(mergeDescendants = true) {}
            .fillMaxWidth()
            .requiredHeightIn(min = BiathlonListItemDefaults.TwoLinesListItemContainerHeight)
            .padding(BiathlonListItemDefaults.ContainerPadding),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = race.description,
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = remember {
                formatDateTime(
                    context,
                    race.start.toEpochMilliseconds(),
                    FORMAT_SHOW_WEEKDAY or FORMAT_SHOW_DATE or FORMAT_SHOW_TIME or FORMAT_ABBREV_ALL,
                )
            },
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
fun AthleteItem(
    athlete: Athlete,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .semantics(mergeDescendants = true) {}
            .fillMaxWidth()
            .requiredHeightIn(min = BiathlonListItemDefaults.TwoLinesListItemContainerHeight)
            .padding(BiathlonListItemDefaults.ContainerPadding),
        horizontalArrangement = Arrangement.spacedBy(BiathlonListItemDefaults.Space),
        verticalAlignment = Alignment.Companion.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(BiathlonListItemDefaults.LeadingAvatarSize)
                .clip(BiathlonListItemDefaults.LeadingAvatarShape)
                .background(color = MaterialTheme.colorScheme.surfaceContainerLow),
        ) {
            AsyncImage(
                model = athlete.avatarUrl,
                contentDescription = null,
            )
        }
        Column(modifier = Modifier.weight(weight = 1f)) {
            Text(
                text = buildAnnotatedString {
                    append(athlete.familyName)
                    withStyle(MaterialTheme.typography.bodyMedium.toSpanStyle()) {
                        append(" ")
                        append(athlete.givenName)
                    }
                },
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = remember { "${athlete.iocCountryCode.toLocale().toEmoji()} ${athlete.iocCountryCode}" },
                modifier = Modifier.semantics {
                    contentDescription = athlete.iocCountryCode.toLocale().displayCountry
                },
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Badge(containerColor = MaterialTheme.colorScheme.secondaryContainer) {
            Text(text = remember { athlete.score.format() })
        }
    }
}

object BiathlonListItemDefaults {
    val OneLineListItemContainerHeight = 56.dp
    val TwoLinesListItemContainerHeight = 72.dp

    val ContainerPadding = PaddingValues(
        horizontal = 16.dp,
        vertical = 8.dp,
    )

    val Space = 16.dp

    val LeadingAvatarSize = 40.dp
    val LeadingAvatarShape: Shape = CircleShape
}

@PreviewLightDark
@Composable
private fun EventPreview() = BiathlonTheme {
    Surface {
        EventItem(event = Event.preview) {}
    }
}

@PreviewLightDark
@Composable
private fun RacePreview() = BiathlonTheme {
    Surface {
        RaceItem(race = Race.preview)
    }
}

@PreviewLightDark
@Composable
private fun AthletePreview() = BiathlonTheme {
    Surface {
        AthleteItem(athlete = Athlete.preview)
    }
}
