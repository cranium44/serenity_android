package live.adabe.serenity.feature_audio.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "songs")
data class MusicObject(
    @PrimaryKey
    val id: Long,
    val name: String,
    val path: String,
    val artist: String,
    val duration: String,
    val album: String,
    val isFavourite: Boolean = false
): Parcelable
