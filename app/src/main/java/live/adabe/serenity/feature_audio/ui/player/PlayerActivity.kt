package live.adabe.serenity.feature_audio.ui.player

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.serenity.R
import live.adabe.serenity.databinding.FragmentPlayerBinding
import live.adabe.serenity.feature_audio.models.MusicObject
import live.adabe.serenity.feature_audio.ui.home.HomeViewModel
import live.adabe.serenity.utils.StringConstants
import live.adabe.serenity.utils.getAlbumArt
import live.adabe.serenity.utils.milliSecondsToTimer
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: FragmentPlayerBinding
    private lateinit var musicObject: MusicObject
    private lateinit var viewModel: HomeViewModel
    private var songs: List<MusicObject> = listOf()
    private var position = -1

    @Inject
    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        intent.run {
            musicObject = getParcelableExtra(StringConstants.MUSIC_OBJECT_KEY)!!
//            position = getIntExtra(StringConstants.MUSIC_POSITION_KEY, -1)
        }
        viewModel.getSortedList()
        innitViewModel()
        bindViews()
        playSong()
    }

    private fun innitViewModel() {

        viewModel.sortedList.value?.let {
            songs = it
            position = songs.indexOf(musicObject)
        }
        Timber.d("the list is ${songs.joinToString { it.toString() }}\nPosition is $position")
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

            skipFwdBtn.setOnClickListener {
                skipNext()
            }

            skipBackBtn.setOnClickListener {
                skipPrev()
            }

            repeatBtn.setOnClickListener {
                repeatSong()
            }

            shuffleBtn.setOnClickListener {
                shuffleSongs()
            }
        }
    }

    private fun repeatSong() {

    }

    private fun shuffleSongs() {

    }

    private fun playSong() {

        binding.run {
            mediaPlayer.setDataSource(musicObject.path)
            mediaPlayer.prepare()
            mediaPlayer.start()
            musicProgress.max = mediaPlayer.duration
            duration.text = milliSecondsToTimer(mediaPlayer.duration.toLong())
            playBtn.setImageResource(R.drawable.ic_pause)
            Timer().scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    runOnUiThread {
                        val currentPosition = mediaPlayer.currentPosition
                        binding.musicProgress.progress = currentPosition
                        binding.currentTime.text = milliSecondsToTimer(currentPosition.toLong())
                    }
                }
            }, 1000, 1000)
            musicProgress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        mediaPlayer.seekTo(progress)
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }

            })
            updateViewsWithInfo(musicObject)
        }
    }

    private fun updateSeekbar() {
        runOnUiThread {
            val currentPosition = mediaPlayer.currentPosition
            binding.musicProgress.progress = currentPosition
        }
    }

    private fun updateViewsWithInfo(musicObject: MusicObject) {
        binding.apply {
            musicName.text = musicObject.name
            musicArtist.text = musicObject.artist
            val albumArt = getAlbumArt(musicObject.path)
            val bmap = albumArt?.let { BitmapFactory.decodeByteArray(albumArt, 0, it.size) }
            bmap?.let { bitmap ->
                Palette.from(bitmap).generate { palette ->
                    palette?.let {
                        val swatch = it.dominantSwatch
                        swatch?.let { swatch1 ->
                            val gradientDrawable =
                                GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    intArrayOf(swatch1.rgb, 0x00000000)
                                )
                            val backgroundGradientDrawable = GradientDrawable(
                                GradientDrawable.Orientation.BOTTOM_TOP,
                                intArrayOf(swatch1.rgb, swatch1.rgb)
                            )

                            coverGradient.setImageDrawable(gradientDrawable)
                            root.background = backgroundGradientDrawable
                            musicName.setTextColor(swatch1.titleTextColor)
                            musicArtist.setTextColor(swatch1.titleTextColor)
                        }}?.run{
                        val gradientDrawable =
                            GradientDrawable(
                                GradientDrawable.Orientation.BOTTOM_TOP,
                                intArrayOf(0xff000000.toInt(), 0x00000000)
                            )
                        val backgroundGradientDrawable = GradientDrawable(
                            GradientDrawable.Orientation.BOTTOM_TOP,
                            intArrayOf(0xff000000.toInt(), 0xff000000.toInt())
                        )

                        coverGradient.setImageDrawable(gradientDrawable)
                        root.background = backgroundGradientDrawable
                        musicName.setTextColor(Color.WHITE)
                        musicArtist.setTextColor(Color.DKGRAY)
                    }
                }
            }
            Glide.with(this@PlayerActivity).load(albumArt).into(coverArt)
        }
    }

    private fun playPause() {
        if (mediaPlayer.isPlaying) {
            binding.playBtn.setImageResource(R.drawable.ic_play)
            mediaPlayer.pause()
            binding.musicProgress.max = mediaPlayer.duration
            updateSeekbar()
        } else {
            binding.playBtn.setImageResource(R.drawable.ic_pause)
            mediaPlayer.start()
            binding.musicProgress.max = mediaPlayer.duration
            updateSeekbar()
        }
    }

    private fun skipNext() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.reset()
            musicObject = viewModel.getNextSong(musicObject)
            updateViewsWithInfo(musicObject)
            mediaPlayer.run {
                setDataSource(musicObject.path)
                prepare()
                start()
                binding.duration.text = milliSecondsToTimer(mediaPlayer.duration.toLong())
                binding.musicProgress.max = mediaPlayer.duration
            }
        }
    }

    private fun skipPrev() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.reset()
            musicObject = viewModel.getPrevSong(musicObject)
            updateViewsWithInfo(musicObject)
            mediaPlayer.run {
                setDataSource(musicObject.path)
                prepare()
                start()
                binding.duration.text = milliSecondsToTimer(mediaPlayer.duration.toLong())
                binding.musicProgress.max = mediaPlayer.duration
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
    }
}