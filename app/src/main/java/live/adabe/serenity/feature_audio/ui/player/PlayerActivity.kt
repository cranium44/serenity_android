package live.adabe.serenity.feature_audio.ui.player

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.serenity.R
import live.adabe.serenity.databinding.FragmentPlayerBinding
import live.adabe.serenity.feature_audio.models.MusicObject
import live.adabe.serenity.utils.StringConstants
import live.adabe.serenity.utils.getAlbumArt
import live.adabe.serenity.utils.milliSecondsToTimer
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: FragmentPlayerBinding
    private lateinit var musicObject: MusicObject
    private var position = -1

    @Inject
    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.run {
            musicObject = getParcelableExtra<MusicObject>(StringConstants.MUSIC_OBJECT_KEY)!!
            position = getIntExtra(StringConstants.MUSIC_POSITION_KEY, -1)
        }
        bindViews()
        playSong()
    }

    private fun bindViews() {
        binding.apply {
            musicName.text = musicObject.name
            musicArtist.text = musicObject.artist
            val albumArt = getAlbumArt(musicObject.path)

            Glide.with(this@PlayerActivity).load(albumArt).into(coverArt)

            playBtn.setOnClickListener {
                playPause()
            }
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
                    runOnUiThread{
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

    private fun updateSeekbar(){
        runOnUiThread{
            val currentPosition = mediaPlayer.currentPosition
            binding.musicProgress.progress = currentPosition
        }
    }

    private fun playPause(){
        if(mediaPlayer.isPlaying){
            binding.playBtn.setImageResource(R.drawable.ic_play)
            mediaPlayer.pause()
            binding.musicProgress.max = mediaPlayer.duration
            updateSeekbar()
        }else{
            binding.playBtn.setImageResource(R.drawable.ic_pause)
            mediaPlayer.start()
            binding.musicProgress.max = mediaPlayer.duration
            updateSeekbar()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
    }
}