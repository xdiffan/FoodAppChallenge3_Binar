package com.challenge.foodappchallenge3.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.challenge.foodappchallenge3.core.ViewHolderBinder
import com.challenge.foodappchallenge3.databinding.CartItemsBinding
import com.challenge.foodappchallenge3.databinding.CheckoutListItemBinding
import com.challenge.foodappchallenge3.model.CartMenu
import com.challenge.foodappchallenge3.presentation.checkout.CheckoutViewHolder


class CartListAdapter(
    private val cartListener: CartListener? = null
) : RecyclerView.Adapter<ViewHolder>()
{
    private val dataDiffer = AsyncListDiffer(this, object : DiffUtil.ItemCallback<CartMenu>() {
        override fun areItemsTheSame(
            oldItem: CartMenu,
            newItem: CartMenu
        ): Boolean {
            return oldItem.cart.id == newItem.cart.id && oldItem.menu.id == newItem.menu.id
        }

        override fun areContentsTheSame(
            oldItem: CartMenu,
            newItem: CartMenu
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return if (cartListener != null) CartItemViewHolder(
            CartItemsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), cartListener
        ) else CheckoutViewHolder(
            CheckoutListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    fun submitData(data: List<CartMenu>) {
        dataDiffer.submitList(data)
    }
    override fun getItemCount(): Int = dataDiffer.currentList.size
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        (holder as ViewHolderBinder<CartMenu>).bind(dataDiffer.currentList[position])
    }
}