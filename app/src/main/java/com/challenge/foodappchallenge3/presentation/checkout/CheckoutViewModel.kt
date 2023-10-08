package com.challenge.foodappchallenge3.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.challenge.foodappchallenge3.data.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckoutViewModel(private val cartRepository: CartRepository) : ViewModel() {
    val cartList = cartRepository.getCartList().asLiveData(Dispatchers.IO)
    fun deleteAll(){
        viewModelScope.launch {
            cartRepository.deleteAll()
        }
    }

}

