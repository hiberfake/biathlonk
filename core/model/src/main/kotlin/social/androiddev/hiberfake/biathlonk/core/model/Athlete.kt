package social.androiddev.hiberfake.biathlonk.core.model

data class Athlete(
    val id: String,
    val avatarUrl: String = "https://assets.biathlonworld.com/image/upload/c_thumb,w_128,h_128,q_auto:best,f_auto/athlete-profile/$id",
    val familyName: String,
    val givenName: String,
    val iocCountryCode: String,
    val rank: Int,
    val score: Int,
) {
    companion object {
        val preview = Athlete(
            id = "BTGER21103199401",
            familyName = "PREUSS",
            givenName = "Franziska",
            iocCountryCode = "GER",
            rank = 1,
            score = 1278,
        )
    }
}
