package social.androiddev.hiberfake.biathlonk.core.common.di

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class ApplicationScope

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val dispatcher: BiathlonResultsDispatchers)

enum class BiathlonResultsDispatchers {
    Default,
    IO,
}
