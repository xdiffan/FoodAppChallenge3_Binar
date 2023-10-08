package com.challenge.foodappchallenge3.presentation.checkout

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.challenge.foodappchallenge3.R
import com.challenge.foodappchallenge3.core.ViewHolderBinder
import com.challenge.foodappchallenge3.databinding.CheckoutListItemBinding
import com.challenge.foodappchallenge3.model.CartMenu
import com.challenge.foodappchallenge3.utils.toCurrencyFormat

class CheckoutViewHolder(
    private val binding: CheckoutListItemBinding,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CartMenu> {
    override fun bind(item: CartMenu) {
        setCartData(item)
    }

    private fun setCartData(item: CartMenu) {
        with(binding) {
            ivMenuImg.load(item.menu.menuImg) {
                crossfade(true)
            }
            tvTotalItem.text =
                itemView.rootView.context.getString(
                    R.string.total_qty,
                    item.cart.itemQuantity.toString()
                )
            tvMenuName.text = item.menu.menuName
            tvMenuPrice.text =item.menu.menuPrice .toCurrencyFormat()
        }
    }


}