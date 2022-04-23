package live.adabe.serenity.feature_audio.navigation

import android.os.Bundle
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

    class PlayerScreen(private var bundle: Bundle?) : SupportAppScreen() {
        override fun getFragment(): Fragment {
            val fragment = PlayerFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}