package social.androiddev.hiberfake.biathlonk.core.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import social.androiddev.hiberfake.biathlonk.core.common.di.BiathlonResultsDispatchers.Default
import social.androiddev.hiberfake.biathlonk.core.common.di.BiathlonResultsDispatchers.IO
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object CoroutinesModule {
    @Provides
    @Dispatcher(Default)
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Dispatcher(IO)
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    @ApplicationScope
    fun provideApplicationScope(@Dispatcher(Default) dispatcher: CoroutineDispatcher) = CoroutineScope(SupervisorJob() + dispatcher)
}
