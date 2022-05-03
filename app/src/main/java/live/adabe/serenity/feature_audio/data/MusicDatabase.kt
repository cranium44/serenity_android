package live.adabe.serenity.feature_audio.data

import androidx.room.Database
import androidx.room.RoomDatabase
import live.adabe.serenity.feature_audio.models.MusicObject

@Database(entities = [MusicObject::class], version = 1)
abstract class MusicDatabase: RoomDatabase() {
    abstract fun dao(): MusicDao
}