package live.adabe.serenity.feature_audio.navigation

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import live.adabe.serenity.MainActivity
import live.adabe.serenity.R
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import javax.inject.Inject

class NavigationService @Inject constructor(cicerone: Cicerone<Router>) : INavigationService {

    private val router: Router = cicerone.router
    private val navigatorHolder: NavigatorHolder = cicerone.navigatorHolder
    override fun openPlayerScreen() {
        navigateTo(Screens.PlayerScreen())
    }

    override fun openHomeScreen() {
        replaceScreen(Screens.HomeScreen())
    }


    override fun attachToActivity(context: Context) {
        context as MainActivity
        navigatorHolder.setNavigator(SupportAppNavigator(context, R.id.navHost))
    }

    override fun detachFromActivity() {
        navigatorHolder.removeNavigator()
    }

    override fun createChain(vararg screens: Screen) {
        router.run { newChain(*screens) }
    }

    private fun navigateTo(screen: SupportAppScreen) {
        CoroutineScope(Dispatchers.Main).launch {
            router.navigateTo(screen)
        }
    }

    private fun replaceScreen(screen: SupportAppScreen) {
        CoroutineScope(Dispatchers.Main).launch {
            router.replaceScreen(screen)
        }
    }

}