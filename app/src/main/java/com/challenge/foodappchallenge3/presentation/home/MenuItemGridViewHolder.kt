package com.challenge.foodappchallenge3.presentation.home

import android.annotation.SuppressLint
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.challenge.foodappchallenge3.core.ViewHolderBinder
import com.challenge.foodappchallenge3.databinding.MenuGridItemBinding
import com.challenge.foodappchallenge3.databinding.MenuListItemBinding
import com.challenge.foodappchallenge3.model.Menu
import com.challenge.foodappchallenge3.utils.toCurrencyFormat

class MenuItemGridViewHolder(
    private val binding: MenuGridItemBinding,
    private val onItemClick: (Menu) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Menu> {
    @SuppressLint("SetTextI18n")
    override fun bind(item: Menu) {
        binding.root.setOnClickListener {
            onItemClick.invoke(item)
        }
        binding.ivMenuImage.load(item.menuImg)
        binding.ivMenuImage.isVisible = true
        binding.tvMenuName.text = item.menuName
        binding.tvMenuPrice.text = item.menuPrice.toCurrencyFormat()
    }
}

class MenuItemLinearViewHolder(
    private val binding: MenuListItemBinding,
    private val onItemClick: (Menu) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Menu> {
    override fun bind(item: Menu) {
        binding.root.setOnClickListener {
            onItemClick.invoke(item)
        }
        binding.ivMenuImg.load(item.menuImg)
        binding.tvMenuName.text = item.menuName
        binding.tvMenuPrice.text = item.menuPrice.toCurrencyFormat()
    }
}
