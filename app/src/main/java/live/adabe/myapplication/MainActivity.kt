package live.adabe.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
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
        getAllMusicFiles()
    }

    override fun onStart() {
        super.onStart()
        getAllMusicFiles()
    }

    private fun initviews() {
        binding.apply { }
    }

    private fun getAllMusicFiles() {
        val tempSongs = mutableListOf<MusicObject>()
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.AudioColumns._ID,
            MediaStore.Audio.AudioColumns.TITLE,
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.ARTIST,
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
                
                tempSongs.add(MusicObject(id, name, path, artist))
            }
            it1.close()
        }

        Log.d("MAIN_ACTIVITY", tempSongs.joinToString { it.toString() } + "file")
    }
}