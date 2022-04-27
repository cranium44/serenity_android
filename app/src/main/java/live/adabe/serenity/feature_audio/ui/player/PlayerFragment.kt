package live.adabe.serenity.feature_audio.ui.player

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.serenity.databinding.FragmentPlayerBinding
import live.adabe.serenity.feature_audio.models.MusicObject
import live.adabe.serenity.feature_audio.ui.adapters.CategoryViewHolder
import live.adabe.serenity.utils.getAlbumArt
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
            musicObject = bundle.getParcelable(CategoryViewHolder.SONG_KEY)!!
        }
        bindViews()
        Timber.d(musicObject.toString())
        return binding.root
    }

    private fun bindViews(){
        binding.apply {
            musicName.text = musicObject.name
            musicArtist.text = musicObject.artist
            val albumArt = getAlbumArt(musicObject.path)

            Glide.with(requireActivity()).load(albumArt).into(coverArt)
        }
    }

}