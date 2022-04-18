package live.adabe.myapplication.feature_audio.models

data class MusicObject(
    val id: Long,
    val name: String,
    val path: String,
    val artist: String,
    val duration: Int,
    val genre: String,
    val isFavourite: Boolean = false
)
