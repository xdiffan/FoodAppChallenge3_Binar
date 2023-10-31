package com.challenge.foodappchallenge3.presentation.checkout

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.challenge.foodappchallenge3.R
import com.challenge.foodappchallenge3.core.ViewHolderBinder
import com.challenge.foodappchallenge3.databinding.CheckoutListItemBinding
import com.challenge.foodappchallenge3.model.Cart
import com.challenge.foodappchallenge3.utils.toCurrencyFormat

class CheckoutViewHolder(
    private val binding: CheckoutListItemBinding
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart> {
    override fun bind(item: Cart) {
        setCartData(item)
    }

    private fun setCartData(item: Cart) {
        with(binding) {
            ivMenuImg.load(item.menuImgUrl) {
                crossfade(true)
            }
            tvTotalItem.text =
                itemView.rootView.context.getString(
                    R.string.total_qty,
                    item.itemQuantity.toString()
                )
            tvMenuName.text = item.menuName
            tvMenuPrice.text = item.menuPrice.toCurrencyFormat()
        }
    }
}
