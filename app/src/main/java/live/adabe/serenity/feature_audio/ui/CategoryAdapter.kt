package live.adabe.serenity.feature_audio.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import live.adabe.serenity.databinding.GroupingViewItemBinding
import live.adabe.serenity.feature_audio.models.CategoryWrapper

class CategoryAdapter(private val musicByCategory: List<CategoryWrapper>) : RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = GroupingViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(musicByCategory[position])
    }

    override fun getItemCount(): Int {
        return musicByCategory.size
    }

}