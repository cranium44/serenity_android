package live.adabe.serenity.feature_audio.ui.home

import android.app.Application
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import live.adabe.serenity.feature_audio.models.CategoryWrapper
import live.adabe.serenity.feature_audio.models.MusicObject
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(private val application: Application) : ViewModel() {

    private var _musicList = getAllMusicFiles()
    private val alphabets = "abcdefghijklmnopqrstuvwxyz"

    //list by name
    private var _musicListByName = MutableLiveData<List<CategoryWrapper>>(mutableListOf())
    val musicListByName: LiveData<List<CategoryWrapper>> = _musicListByName

    //sorted list
    private var _sortedList = MutableLiveData<List<MusicObject>>()
    val sortedList: LiveData<List<MusicObject>> = _sortedList

    //shuffle
    private var isShuffleOn = false

    private fun getAllMusicFiles(): MutableList<MusicObject> {

        val tempSongs = mutableListOf<MusicObject>()
        val uriExt = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val uriInt = MediaStore.Audio.Media.INTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM
        )
        val selection = "${MediaStore.Audio.Media.DURATION} >= ?"
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
                val duration = it1.getString(4)
                val album = it1.getString(5)

                tempSongs.add(
                    MusicObject(
                        id,
                        name,
                        path,
                        artist,
                        duration,
                        album
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
                val duration = it1.getString(4)
                val album = it1.getString(5)

                tempSongs.add(
                    MusicObject(
                        id,
                        name,
                        path,
                        artist,
                        duration,
                        album
                    )
                )
            }
            it1.close()
        }

        return tempSongs
    }

    fun setIsShuffleOn(boolean: Boolean){
        isShuffleOn = boolean
    }

    fun getIsShuffleOn(): Boolean = isShuffleOn

    fun getMusicByName() {
        var music = mutableListOf<CategoryWrapper>()
        for (letter in alphabets) {
            music.add(
                CategoryWrapper(
                    header = letter.uppercase(),
                    content = _musicList.let { list ->
                        Timber.d("music list")
                        return@let list.filter { it.name[0].uppercase() == letter.uppercase() }
                    }
                )
            )
        }
        music = music.filter { it.content.isNotEmpty() } as MutableList<CategoryWrapper>
        _musicListByName.postValue(music)
    }

    fun getSortedList() {
        _sortedList.postValue(_musicList.sortedBy { it.name })
    }

    fun getNextSong(currentSong: MusicObject): MusicObject {
        var position = _musicList.sortedBy { it.name }.indexOf(currentSong)
        return if (!isShuffleOn) _musicList.sortedBy { it.name }[++position]
        else _musicList.sortedBy { it.name }[Random.nextInt(
            _musicList.size
        )]
    }

    fun getPrevSong(currentSong: MusicObject): MusicObject {
        var position = _musicList.sortedBy { it.name }.indexOf(currentSong)
        return if (!isShuffleOn) _musicList.sortedBy { it.name }[--position]
        else _musicList.sortedBy { it.name }[Random.nextInt(
            _musicList.size
        )]
    }
}