package social.androiddev.hiberfake.biathlonk.core.model

data class Season(
    val id: String,
    val description: String,
    val isCurrentlyScheduled: Boolean = false,
    val hasCurrentResults: Boolean = false,
) {
    companion object {
        val previewIsCurrentlyScheduled = Season(
            id = "2526",
            description = "2025/2026",
            isCurrentlyScheduled = true,
        )
        val previewHasCurrentResults = Season(
            id = "2425",
            description = "2024/2025",
            hasCurrentResults = true,
        )
    }
}
