package live.adabe.serenity.feature_audio.di

import android.app.Application
import android.media.MediaPlayer
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import live.adabe.serenity.feature_audio.data.MusicDao
import live.adabe.serenity.feature_audio.data.MusicDatabase
import live.adabe.serenity.feature_audio.navigation.INavigationService
import live.adabe.serenity.feature_audio.navigation.NavigationService
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Provides
    @Singleton
    fun providesCicerone(): Cicerone<Router> {
        return Cicerone.create()
    }

    @Provides
    @Singleton
    fun provideNavigationService(cicerone: Cicerone<Router>): INavigationService {
        return NavigationService(cicerone)
    }

    @Provides
    @Singleton
    fun provideMediaPlayer() = MediaPlayer()

    @Provides
    @Singleton
    fun provideDB(application: Application): MusicDatabase {
        return Room.databaseBuilder(application, MusicDatabase::class.java, "songs_db")
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideDao(db: MusicDatabase): MusicDao = db.dao()
}