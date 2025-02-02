package social.androiddev.hiberfake.biathlonk.core.network.api

import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query
import social.androiddev.hiberfake.biathlonk.core.network.model.NetworkCup
import social.androiddev.hiberfake.biathlonk.core.network.model.NetworkCupResults
import social.androiddev.hiberfake.biathlonk.core.network.model.NetworkEvent
import social.androiddev.hiberfake.biathlonk.core.network.model.NetworkRace
import social.androiddev.hiberfake.biathlonk.core.network.model.NetworkSeason

interface BiathlonResultsApi {
    @GET("Seasons")
    suspend fun getSeasons(): ApiResponse<List<NetworkSeason>>

    @GET("Events")
    suspend fun getEvents(
        @Query("SeasonId") seasonId: String,
        @Query("Level") level: Int = 1,
        @Query("Language") language: String = "DE",
    ): ApiResponse<List<NetworkEvent>>

    @GET("competitions")
    suspend fun getRaces(
        @Query("EventId") eventId: String,
        @Query("Language") language: String = "DE",
    ): ApiResponse<List<NetworkRace>>

    @GET("Cups")
    suspend fun getCups(@Query("SeasonId") seasonId: String): ApiResponse<List<NetworkCup>>

    @GET("CupResults")
    suspend fun getCupResults(@Query("CupId") cupId: String): ApiResponse<NetworkCupResults>
}
