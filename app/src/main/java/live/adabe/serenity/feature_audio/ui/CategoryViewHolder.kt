package live.adabe.serenity.feature_audio.ui

import androidx.recyclerview.widget.RecyclerView
import live.adabe.serenity.databinding.GroupingViewItemBinding
import live.adabe.serenity.feature_audio.models.CategoryWrapper

class CategoryViewHolder(private val binding: GroupingViewItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(categoryWrapper: CategoryWrapper){
        binding.titleText.text = categoryWrapper.header
        //Todo initialise adapter here
    }
}