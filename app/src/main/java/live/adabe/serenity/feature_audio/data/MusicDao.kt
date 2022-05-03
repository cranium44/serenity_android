package live.adabe.serenity.feature_audio.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import live.adabe.serenity.feature_audio.models.MusicObject

@Dao
interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSong(musicObject: MusicObject)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMultipleSongs(vararg musicObject: MusicObject)

    @Query("SELECT * FROM songs")
    suspend fun getAllSongs(): List<MusicObject>
}