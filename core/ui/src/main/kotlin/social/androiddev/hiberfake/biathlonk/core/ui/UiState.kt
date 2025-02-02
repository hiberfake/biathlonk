package social.androiddev.hiberfake.biathlonk.core.ui

/**
 * A sealed hierarchy representing the state of a UI component.
 */
sealed interface UiState<out T> {
    /**
     * The UI is in a loading state.
     */
    data object Loading : UiState<Nothing>

    /**
     * The UI has successfully loaded the data.
     */
    data class Success<T>(val data: T) : UiState<T>

    /**
     * The UI has encountered a failure while loading the data.
     */
    data class Failure(val exception: Throwable) : UiState<Nothing>
}
