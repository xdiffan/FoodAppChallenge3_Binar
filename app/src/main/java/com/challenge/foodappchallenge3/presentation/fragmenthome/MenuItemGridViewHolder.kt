package com.challenge.foodappchallenge3.presentation.fragmenthome

import androidx.recyclerview.widget.RecyclerView
import com.challenge.foodappchallenge3.core.ViewHolderBinder
import com.challenge.foodappchallenge3.databinding.MenuGridItemBinding
import com.challenge.foodappchallenge3.databinding.MenuListItemBinding
import com.challenge.foodappchallenge3.model.Menu

class MenuItemGridViewHolder(
    private val binding: MenuGridItemBinding,
    private val onItemClick: (Menu) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Menu> {
    override fun bind(item: Menu) {
        binding.root.setOnClickListener {
            onItemClick.invoke(item)
        }
        binding.ivMenuImage.setImageResource(item.menuImg)
        binding.tvMenuName.text = item.menuName
        binding.tvMenuPrice.text = "Rp. ${item.menuPrice.toInt()}"
    }
}

class MenuItemLinearViewHolder(
    private val binding : MenuListItemBinding,
    private val onItemClick : (Menu) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Menu> {
    override fun bind(item: Menu) {
        binding.root.setOnClickListener {
            onItemClick.invoke(item)
        }
        binding.ivMenuImg.setImageResource(item.menuImg)
        binding.tvMenuName.text = item.menuName
        binding.tvMenuPrice.text = "Rp. ${item.menuPrice.toInt()}"
    }
}