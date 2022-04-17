package live.adabe.myapplication.feature_audio.ui

import androidx.recyclerview.widget.RecyclerView
import live.adabe.myapplication.databinding.GroupingViewItemBinding
import live.adabe.myapplication.feature_audio.models.CategoryWrapper

class CategoryViewHolder(private val binding: GroupingViewItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(categoryWrapper: CategoryWrapper){
        binding.titleText.text = categoryWrapper.header
        //Todo initialise adapter here
    }
}