package social.androiddev.hiberfake.biathlonk.core.network.di

import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import social.androiddev.hiberfake.biathlonk.core.network.BuildConfig
import social.androiddev.hiberfake.biathlonk.core.network.api.BiathlonResultsApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    @Provides
    @Singleton
    fun provideCallFactory(): Call.Factory = OkHttpClient.Builder()
        .addLoggingInterceptor()
        .build()

    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory {
        val json = Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
            isLenient = true
//            serializersModule = SerializersModule {
//                contextual(LocalDate::class, LocalDateSerializer)
//                contextual(LocalTime::class, LocalTimeSerializer)
//                contextual(OffsetDateTime::class, OffsetDateTimeSerializer)
//                contextual(UUID::class, UuidSerializer)
//            }
        }

        return json.asConverterFactory("application/json".toMediaType())
    }

    @Provides
    @Singleton
    fun provideCallAdapterFactory(): CallAdapter.Factory = ApiResponseCallAdapterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(
        callFactory: Lazy<Call.Factory>,
        converterFactory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory,
    ): Retrofit = Retrofit.Builder()
        .callFactory(callFactory.get())
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(callAdapterFactory)
        .build()

    @Provides
    @Singleton
    fun provideBiathlonResultsApi(retrofit: Retrofit): BiathlonResultsApi = retrofit.create(
        BiathlonResultsApi::class.java,
    )
}

private fun OkHttpClient.Builder.addLoggingInterceptor() = apply {
    if (BuildConfig.DEBUG) {
        addInterceptor(HttpLoggingInterceptor().setLevel(BODY))
    }
}
