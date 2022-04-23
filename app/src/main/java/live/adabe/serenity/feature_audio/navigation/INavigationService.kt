package live.adabe.serenity.feature_audio.navigation

import android.content.Context
import android.os.Bundle
import ru.terrakok.cicerone.Screen


interface INavigationService {

    fun openPlayerScreen(bundle: Bundle? = null)

    fun openHomeScreen()

    fun attachToActivity(context: Context)

    fun detachFromActivity()

    fun createChain(vararg screens: Screen)
}