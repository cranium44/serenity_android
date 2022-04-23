package live.adabe.serenity.feature_audio.ui

import android.app.Application
import android.media.MediaPlayer
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import live.adabe.serenity.databinding.GroupingViewItemBinding
import live.adabe.serenity.feature_audio.models.CategoryWrapper
import live.adabe.serenity.feature_audio.models.MusicObject
import javax.inject.Inject

class CategoryViewHolder @Inject constructor(private val binding: GroupingViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

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
            Toast.makeText(this@CategoryViewHolder.itemView.context, "Artist ${musicObject.artist}", Toast.LENGTH_SHORT)
                .show()

            try {
                val player = MediaPlayer()

                player.setDataSource(musicObject.path)
                player.prepare()
                player.start()

            } catch (t: Throwable) {

            }
        }

        override fun onMusicPlay(musicObject: MusicObject, button: ImageButton) {
            TODO("Not yet implemented")
        }

    }
}