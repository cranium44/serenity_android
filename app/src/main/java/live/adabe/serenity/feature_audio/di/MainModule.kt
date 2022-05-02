package live.adabe.serenity.feature_audio.di

import android.media.MediaPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
    fun providesCicerone(): Cicerone<Router>{
        return Cicerone.create()
    }

    @Provides
    @Singleton
    fun provideNavigationService(cicerone: Cicerone<Router>): INavigationService{
        return NavigationService(cicerone)
    }

    @Provides
    @Singleton
    fun provideMediaPlayer() = MediaPlayer()
}