package com.challenge.foodappchallenge3.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.challenge.foodappchallenge3.core.ViewHolderBinder
import com.challenge.foodappchallenge3.databinding.MenuGridItemBinding
import com.challenge.foodappchallenge3.databinding.MenuListItemBinding
import com.challenge.foodappchallenge3.model.Menu

class MenuListAdapter(
    var adapterLayoutMode: AdapterLayoutMode,
    private val onItemClick: (Menu) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val differ = AsyncListDiffer(this,
        object : DiffUtil.ItemCallback<Menu>() {
            override fun areItemsTheSame(
                oldItem: Menu,
                newItem: Menu,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Menu,
                newItem: Menu,
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        })

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return when (viewType){
            AdapterLayoutMode.GRID.ordinal -> {
                MenuItemGridViewHolder(
                    binding = MenuGridItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onItemClick
                )
            }
            else -> {
                MenuItemLinearViewHolder(
                    binding = MenuListItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onItemClick
                )
            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        return adapterLayoutMode.ordinal
    }
    fun setData(data: List<Menu>) {
        differ.submitList(data)
    }
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        (holder as ViewHolderBinder<Menu>).bind(differ.currentList[position])
    }
    override fun getItemCount(): Int = differ.currentList.size

}