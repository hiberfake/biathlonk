package social.androiddev.hiberfake.biathlonk.core.testing

import kotlinx.collections.immutable.persistentListOf
import social.androiddev.hiberfake.biathlonk.core.model.CupResults
import social.androiddev.hiberfake.biathlonk.core.model.Event
import social.androiddev.hiberfake.biathlonk.core.model.Race
import social.androiddev.hiberfake.biathlonk.core.model.Season
import social.androiddev.hiberfake.biathlonk.core.network.model.NetworkSeason

val seasonsTestData = persistentListOf(
    Season.previewIsCurrentlyScheduled,
    Season.previewHasCurrentResults,
)

val eventsTestData = persistentListOf(Event.preview)

val racesTestData = persistentListOf(Race.preview)

val cupResultsTestData = persistentListOf(CupResults.preview)

val networkSeasonsTestData = listOf(
    NetworkSeason(
        id = "2526",
        description = "2025/2026",
        isCurrentlyScheduled = true,
    ),
    NetworkSeason(
        id = "2425",
        description = "2024/2025",
        hasCurrentResults = true,
    ),
)
