package com.challenge.foodappchallenge3.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.challenge.foodappchallenge3.core.ViewHolderBinder
import com.challenge.foodappchallenge3.databinding.CategoryItemBinding
import com.challenge.foodappchallenge3.model.Category


class CategoryListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val differ = AsyncListDiffer(this,
        object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(
                oldItem: Category,
                newItem: Category,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Category,
                newItem: Category,
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        })
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return CategoryItemViewHolder(
            binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        (holder as ViewHolderBinder<Category>).bind(differ.currentList[position])
    }

    fun setData(data: List<Category>) {
        differ.submitList(data)
    }

}