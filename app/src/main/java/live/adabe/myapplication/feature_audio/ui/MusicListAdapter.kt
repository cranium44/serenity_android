package live.adabe.myapplication.feature_audio.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import live.adabe.myapplication.databinding.MusicListItemBinding
import live.adabe.myapplication.feature_audio.models.MusicObject

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
            }
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
    }
}