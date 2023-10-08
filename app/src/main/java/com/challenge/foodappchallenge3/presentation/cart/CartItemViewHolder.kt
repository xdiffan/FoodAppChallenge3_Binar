package com.challenge.foodappchallenge3.presentation.cart


import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.challenge.foodappchallenge3.core.ViewHolderBinder
import com.challenge.foodappchallenge3.databinding.CartItemsBinding
import com.challenge.foodappchallenge3.model.Cart
import com.challenge.foodappchallenge3.model.CartMenu
import com.challenge.foodappchallenge3.utils.doneEditing
import com.challenge.foodappchallenge3.utils.toCurrencyFormat

class CartItemViewHolder(
    private val binding: CartItemsBinding,
    private val cartListener: CartListener?,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CartMenu> {

    override fun bind(item: CartMenu) {
        setCartData(item)
        setCartNotes(item)
        setClickListener(item)
    }

    private fun setClickListener(item: CartMenu) {
        binding.apply {
            icMinus.setOnClickListener { cartListener?.onMinusTotalItemCartClicked(item.cart) }
            icAdd.setOnClickListener { cartListener?.onPlusTotalItemCartClicked(item.cart) }
            icTrash.setOnClickListener { cartListener?.onRemoveCartClicked(item.cart) }
            itemView.setOnClickListener { cartListener?.onCartClicked(item) }
        }
    }

    private fun setCartNotes(item: CartMenu) {
        binding.etNotes.setText(item.cart.itemNotes)
        binding.etNotes.doneEditing {
            binding.etNotes.clearFocus()
            val newItem = item.cart.copy().apply {
                itemNotes = binding.etNotes.text.toString().trim()
            }
            cartListener?.onUserDoneEditingNotes(newItem)
        }
    }

    private fun setCartData(item: CartMenu) {
        binding.apply {
            binding.ivMenuImg.load(item.menu.menuImg){crossfade(true)}
        }
        binding.tvMenuName.text = item.menu.menuName
        binding.tvMenuPrice.text=item.menu.menuPrice.toCurrencyFormat()
        binding.tvMenuPriceTotal.text = (item.menu.menuPrice * item.cart.itemQuantity).toCurrencyFormat()
        binding.tvCounting.text = item.cart.itemQuantity.toString()
    }

}


interface CartListener{
    fun onCartClicked(item: CartMenu)
    fun onPlusTotalItemCartClicked(cart: Cart)
    fun onMinusTotalItemCartClicked(cart: Cart)
    fun onRemoveCartClicked(cart: Cart)
    fun onUserDoneEditingNotes(cart: Cart)
}