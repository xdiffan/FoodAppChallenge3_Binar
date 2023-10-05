package com.challenge.foodappchallenge3.presentation.fragmenthome

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.challenge.foodappchallenge3.R
import com.challenge.foodappchallenge3.model.Category
class CategoryListAdapter(
    private val categoryList: List<Category>
) : RecyclerView.Adapter<CategoryListAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCategoryName: TextView = itemView.findViewById(R.id.tv_category_name)
       val ivCategoryImg:ImageView= itemView.findViewById(R.id.iv_category_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvCategoryName.text = categoryList[position].categoryName
        holder.ivCategoryImg.load(categoryList[position].categoryImgSrc)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}