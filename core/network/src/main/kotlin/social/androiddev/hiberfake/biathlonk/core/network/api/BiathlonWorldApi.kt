package social.androiddev.hiberfake.biathlonk.core.network.api

import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BiathlonWorldApi {
    @GET("image/upload/c_thumb,w_128,h_128/athlete-profile/{athleteId}")
    suspend fun getAthleteProfileImage(@Path("athleteId") athleteId: String): ApiResponse<String>
}
