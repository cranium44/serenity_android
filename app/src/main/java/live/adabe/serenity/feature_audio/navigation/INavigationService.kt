package live.adabe.serenity.feature_audio.navigation

import android.content.Context
import ru.terrakok.cicerone.Screen


interface INavigationService {

    fun openPlayerScreen()

    fun openHomeScreen()

    fun attachToActivity(context: Context)

    fun detachFromActivity()

    fun createChain(vararg screens: Screen)
}