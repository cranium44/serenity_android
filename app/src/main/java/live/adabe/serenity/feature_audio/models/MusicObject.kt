package live.adabe.serenity.feature_audio.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class MusicObject(
    val id: Long,
    val name: String,
    val path: String,
    val artist: String,
    val duration: String,
    val album: String,
    val isFavourite: Boolean = false
): Parcelable
