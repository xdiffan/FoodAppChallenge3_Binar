package com.challenge.foodappchallenge3.presentation.cart

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.challenge.foodappchallenge3.core.ViewHolderBinder
import com.challenge.foodappchallenge3.databinding.CartItemsBinding
import com.challenge.foodappchallenge3.model.Cart
import com.challenge.foodappchallenge3.utils.doneEditing
import com.challenge.foodappchallenge3.utils.toCurrencyFormat

class CartItemViewHolder(
    private val binding: CartItemsBinding,
    private val cartListener: CartListener?
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart> {

    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
        setClickListener(item)
    }

    private fun setClickListener(item: Cart) {
        binding.apply {
            icMinus.setOnClickListener { cartListener?.onMinusTotalItemCartClicked(item) }
            icAdd.setOnClickListener { cartListener?.onPlusTotalItemCartClicked(item) }
            icTrash.setOnClickListener { cartListener?.onRemoveCartClicked(item) }
            itemView.setOnClickListener { cartListener?.onCartClicked(item) }
        }
    }

    private fun setCartNotes(item: Cart) {
        binding.etNotes.setText(item.itemNotes)
        binding.etNotes.doneEditing {
            binding.etNotes.clearFocus()
            val newItem = item.copy().apply {
                itemNotes = binding.etNotes.text.toString().trim()
            }
            cartListener?.onUserDoneEditingNotes(newItem)
        }
    }

    private fun setCartData(item: Cart) {
        binding.apply {
            binding.ivMenuImg.load(item.menuImgUrl) { crossfade(true) }
        }
        binding.tvMenuName.text = item.menuName
        binding.tvMenuPrice.text = item.menuPrice.toCurrencyFormat()
        binding.tvMenuPriceTotal.text = (item.menuPrice * item.itemQuantity).toCurrencyFormat()
        binding.tvCounting.text = item.itemQuantity.toString()
    }
}

interface CartListener {
    fun onCartClicked(item: Cart)
    fun onPlusTotalItemCartClicked(cart: Cart)
    fun onMinusTotalItemCartClicked(cart: Cart)
    fun onRemoveCartClicked(cart: Cart)
    fun onUserDoneEditingNotes(cart: Cart)
}
