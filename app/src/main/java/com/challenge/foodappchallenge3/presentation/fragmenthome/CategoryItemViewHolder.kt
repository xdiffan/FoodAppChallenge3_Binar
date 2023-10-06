package com.challenge.foodappchallenge3.presentation.fragmenthome

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.challenge.foodappchallenge3.core.ViewHolderBinder
import com.challenge.foodappchallenge3.databinding.CategoryItemBinding
import com.challenge.foodappchallenge3.model.Category

class CategoryItemViewHolder(
    private val binding: CategoryItemBinding
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Category> {

    override fun bind(item: Category) {
        binding.tvCategoryName.text = item.categoryName
        binding.ivCategoryImage.load(item.categoryImgSrc)
    }
}