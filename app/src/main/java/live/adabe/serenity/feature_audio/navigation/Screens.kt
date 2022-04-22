package live.adabe.serenity.feature_audio.navigation

import androidx.fragment.app.Fragment
import live.adabe.serenity.feature_audio.ui.home.HomeFragment
import live.adabe.serenity.feature_audio.ui.player.PlayerFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class HomeScreen: SupportAppScreen(){
        override fun getFragment(): Fragment {
            return HomeFragment()
        }
    }

    class PlayerScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return PlayerFragment()
        }
    }

}