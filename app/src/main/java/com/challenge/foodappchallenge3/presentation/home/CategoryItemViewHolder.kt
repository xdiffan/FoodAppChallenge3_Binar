package com.challenge.foodappchallenge3.presentation.home

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.challenge.foodappchallenge3.core.ViewHolderBinder
import com.challenge.foodappchallenge3.databinding.CategoryItemBinding
import com.challenge.foodappchallenge3.model.Category

class CategoryItemViewHolder(
    private val binding: CategoryItemBinding,
    val onItemClick: (Category) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Category> {

    override fun bind(item: Category) {
        with(item) {
            binding.tvCategoryName.text = item.categoryName
            binding.ivCategoryImage.load(item.categoryImgSrc)
            itemView.setOnClickListener { onItemClick(this) }
        }
    }
}