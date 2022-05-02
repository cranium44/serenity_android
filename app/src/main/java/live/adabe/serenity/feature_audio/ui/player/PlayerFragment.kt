package live.adabe.serenity.feature_audio.ui.player

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.serenity.R
import live.adabe.serenity.databinding.FragmentPlayerBinding
import live.adabe.serenity.feature_audio.models.MusicObject
import live.adabe.serenity.feature_audio.ui.adapters.CategoryViewHolder
import live.adabe.serenity.utils.getAlbumArt
import live.adabe.serenity.utils.milliSecondsToTimer
import timber.log.Timber
import java.util.*
import javax.inject.Inject





@AndroidEntryPoint
class PlayerFragment : Fragment() {

    private lateinit var binding: FragmentPlayerBinding
    private lateinit var musicObject: MusicObject

    @Inject
    lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPlayerBinding.inflate(inflater, container, false)
        arguments?.let { bundle ->
            musicObject = bundle.getParcelable(CategoryViewHolder.SONG_KEY)!!
        }
        bindViews()
        playSong()
        return binding.root
    }

    private fun bindViews() {
        binding.apply {
            musicName.text = musicObject.name
            musicArtist.text = musicObject.artist
            val albumArt = getAlbumArt(musicObject.path)

            Glide.with(requireActivity()).load(albumArt).into(coverArt)
        }
    }

    private fun playSong() {

        binding.run {
            mediaPlayer.setDataSource(musicObject.path)
            mediaPlayer.prepare()
            mediaPlayer.start()
            musicProgress.max = mediaPlayer.duration
            duration.text = milliSecondsToTimer(mediaPlayer.duration.toLong())
            playBtn.setImageResource(R.drawable.ic_pause)
            Timer().scheduleAtFixedRate(object : TimerTask(){
                override fun run() {
                    requireActivity().runOnUiThread{
                        val currentPosition = mediaPlayer.currentPosition
                        binding.musicProgress.progress = currentPosition
                        binding.currentTime.text = milliSecondsToTimer(currentPosition.toLong())
                    }
                }
            }, 1000, 1000)
            musicProgress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if(fromUser){
                        mediaPlayer.seekTo(progress * 1000)
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }

            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
    }
}