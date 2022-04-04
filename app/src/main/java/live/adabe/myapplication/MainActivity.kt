package live.adabe.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaDataSource
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.myapplication.databinding.ActivityMainBinding
import live.adabe.myapplication.feature_audio.models.MusicObject
import live.adabe.myapplication.feature_audio.ui.MusicListAdapter
import java.util.concurrent.TimeUnit

//@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var songsAdapter: MusicListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        songsAdapter = MusicListAdapter(getAllMusicFiles(), listener)
        initviews()
    }

    private fun initviews() {
        binding.apply {
            musicListRv.adapter = songsAdapter
            musicListRv.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun getAllMusicFiles(): List<MusicObject> {
        val tempSongs = mutableListOf<MusicObject>()
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.AudioColumns._ID,
            MediaStore.Audio.AudioColumns.TITLE,
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.ARTIST,
            MediaStore.Audio.AudioColumns.DISPLAY_NAME
        )
        val selection = "${MediaStore.Audio.AudioColumns.DURATION} >= ?"
        val selectionArgs = arrayOf(TimeUnit.MILLISECONDS.convert(30L, TimeUnit.SECONDS).toString())

        val cursor = this.contentResolver.query(
            uri,
            projection,
            selection,
            selectionArgs,
            "${MediaStore.Audio.AudioColumns.TITLE} ASC",
        )

        cursor?.let { it1 ->
            while (it1.moveToNext()) {
                val id = it1.getLong(0)
                val name = it1.getString(1)
                val path = it1.getString(2)
                val artist = it1.getString(3)
                val displayName = it1.getColumnName(4)

                tempSongs.add(MusicObject(id, name, path, artist, displayName))
            }
            it1.close()
        }

        Log.d("MAIN_ACTIVITY", tempSongs.joinToString { it.toString() } + "file")

        return tempSongs
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
            Toast.makeText(this@MainActivity, "Artist ${musicObject.artist}", Toast.LENGTH_SHORT).show()

            try{
                val player = MediaPlayer()

                player.setDataSource(musicObject.path)
                player.prepare()
                player.start()

            }catch (t: Throwable){
                Toast.makeText(this@MainActivity, "Error Playing Audio", Toast.LENGTH_SHORT).show()
            }
        }
    }
}