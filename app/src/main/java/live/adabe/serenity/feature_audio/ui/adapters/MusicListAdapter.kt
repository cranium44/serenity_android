package live.adabe.serenity.feature_audio.ui.adapters

import android.media.MediaMetadataRetriever
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import live.adabe.serenity.databinding.MusicListItemBinding
import live.adabe.serenity.feature_audio.models.MusicObject

class MusicListAdapter(
    private var songs: List<MusicObject>,
    private var listener: OnMusicItemClickListener
) :
    RecyclerView.Adapter<MusicListAdapter.MusicListViewHolder>() {

    inner class MusicListViewHolder(
        private var binding: MusicListItemBinding,
        private var listener: OnMusicItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: MusicObject) {
            binding.apply {
                songName.text = song.name
                songArtist.text = song.artist
                root.setOnClickListener {
                    listener.onItemClick(song)
                }
                val image = getAlbumArt(song.path)
                image?.let { bytes ->
                    Glide.with(root.context).load(bytes).into(albumArt)
                }
            }
        }

        private fun getAlbumArt(uri: String): ByteArray? {
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(uri)
            val art = retriever.embeddedPicture
            retriever.close()
            return art
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicListViewHolder {
        val binding =
            MusicListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicListViewHolder(binding, listener)
    }


    override fun getItemCount(): Int = songs.size

    override fun onBindViewHolder(holder: MusicListViewHolder, position: Int) {
        holder.bind(songs[position])
    }

    interface OnMusicItemClickListener {
        fun onItemClick(musicObject: MusicObject)
        fun onMusicPlay(musicObject: MusicObject, button: ImageButton)
    }
}