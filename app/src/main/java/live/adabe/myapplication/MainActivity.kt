package live.adabe.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaDataSource
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.myapplication.databinding.ActivityMainBinding
import live.adabe.myapplication.feature_audio.models.MusicObject
import live.adabe.myapplication.feature_audio.navigation.INavigationService
import live.adabe.myapplication.feature_audio.navigation.NavigationService
import live.adabe.myapplication.feature_audio.ui.MusicListAdapter
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var navigationService: INavigationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        getPermissions()
        setContentView(binding.root)

        navigationService.openHomeScreen()
    }




    private fun getPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 100
            )
        }
    }

    private val listener = object : MusicListAdapter.OnMusicItemClickListener {
        override fun onItemClick(musicObject: MusicObject) {
            Toast.makeText(this@MainActivity, "Artist ${musicObject.artist}", Toast.LENGTH_SHORT)
                .show()

            try {
                val player = MediaPlayer()

                player.setDataSource(musicObject.path)
                player.prepare()
                player.start()

            } catch (t: Throwable) {
                Toast.makeText(this@MainActivity, "Error Playing Audio", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onMusicPlay(musicObject: MusicObject, button: ImageButton) {
            TODO("Not yet implemented")
        }
    }
}