package live.adabe.serenity.feature_audio.ui.adapters

import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import live.adabe.serenity.databinding.GroupingViewItemBinding
import live.adabe.serenity.feature_audio.models.CategoryWrapper
import live.adabe.serenity.feature_audio.models.MusicObject
import live.adabe.serenity.feature_audio.navigation.INavigationService

class CategoryViewHolder constructor(
    private val binding: GroupingViewItemBinding,
    private val navigationService: INavigationService
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        const val SONG_KEY = "song"
    }

    fun bind(categoryWrapper: CategoryWrapper) {
        binding.apply {
            titleText.text = categoryWrapper.header
            contentRv.apply {
                adapter = MusicListAdapter(categoryWrapper.content, listener)
                layoutManager = LinearLayoutManager(this.context)
            }
        }

    }

    private val listener = object : MusicListAdapter.OnMusicItemClickListener {
        override fun onItemClick(musicObject: MusicObject) {
            navigationService.openPlayerScreen(Bundle().also { bundle ->
                bundle.putParcelable(SONG_KEY, musicObject)
            })
        }

        override fun onMusicPlay(musicObject: MusicObject, button: ImageButton) {
            TODO("Not yet implemented")
        }

    }
}