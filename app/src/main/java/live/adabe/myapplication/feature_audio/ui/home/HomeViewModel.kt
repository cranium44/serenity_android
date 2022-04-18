package live.adabe.myapplication.feature_audio.ui.home

import android.app.Application
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import live.adabe.myapplication.feature_audio.models.CategoryWrapper
import live.adabe.myapplication.feature_audio.models.MusicObject
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val application: Application) : ViewModel() {

    private var _musicList = MutableLiveData<List<MusicObject>>()
    private val alphabets = listOf("a","b", "c","d", "e")

    //list by name
    private var _musicListByName = MutableLiveData<List<CategoryWrapper>>()
    val musicListByName: LiveData<List<CategoryWrapper>> = _musicListByName

    init {
        _musicList.postValue(getAllMusicFiles())
    }

    private fun getAllMusicFiles(): List<MusicObject> {
        val tempSongs = mutableListOf<MusicObject>()
        val uriExt = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val uriInt = MediaStore.Audio.Media.INTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.AudioColumns._ID,
            MediaStore.Audio.AudioColumns.TITLE,
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.ARTIST,
            MediaStore.Audio.AudioColumns.DURATION,
            MediaStore.Audio.AudioColumns.GENRE
        )
        val selection = "${MediaStore.Audio.AudioColumns.DURATION} >= ?"
        val selectionArgs = arrayOf(TimeUnit.MILLISECONDS.convert(30L, TimeUnit.SECONDS).toString())

        val cursorExt = application.contentResolver.query(
            uriExt,
            projection,
            selection,
            selectionArgs,
            "${MediaStore.Audio.AudioColumns.TITLE} ASC",
        )

        val cursorInt = application.contentResolver.query(
            uriInt,
            projection,
            selection,
            selectionArgs,
            "${MediaStore.Audio.AudioColumns.TITLE} ASC",
        )

        cursorExt?.let { it1 ->
            while (it1.moveToNext()) {
                val id = it1.getLong(0)
                val name = it1.getString(1)
                val path = it1.getString(2)
                val artist = it1.getString(3)
                val duration = it1.getColumnName(4)
                val genre = it1.getColumnName(5)

                tempSongs.add(
                    MusicObject(
                        id,
                        name,
                        path,
                        artist,
                        duration.toIntOrNull() ?: 0,
                        genre
                    )
                )
            }
            it1.close()
        }

        cursorInt?.let { it1 ->
            while (it1.moveToNext()) {
                val id = it1.getLong(0)
                val name = it1.getString(1)
                val path = it1.getString(2)
                val artist = it1.getString(3)
                val duration = it1.getColumnName(4)
                val genre = it1.getColumnName(5)

                tempSongs.add(
                    MusicObject(
                        id,
                        name,
                        path,
                        artist,
                        duration.toIntOrNull() ?: 0,
                        genre
                    )
                )
            }
            it1.close()
        }

        Timber.d(tempSongs.joinToString {
            it.toString()
        })

        return tempSongs
    }

    fun getMusicByName(){

    }
}