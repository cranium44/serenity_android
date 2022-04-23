package live.adabe.serenity.feature_audio.ui.player

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.serenity.databinding.FragmentPlayerBinding
import live.adabe.serenity.feature_audio.models.MusicObject
import live.adabe.serenity.feature_audio.ui.CategoryViewHolder
import timber.log.Timber


@AndroidEntryPoint
class PlayerFragment : Fragment() {

    private lateinit var binding: FragmentPlayerBinding
    private lateinit var musicObject: MusicObject

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPlayerBinding.inflate(inflater,container,false)

        arguments?.let { bundle ->
            musicObject = bundle.getParcelable<MusicObject>(CategoryViewHolder.SONG_KEY)!!
        }

        Timber.d(musicObject.toString())
        return binding.root
    }


}