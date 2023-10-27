package com.challenge.foodappchallenge3.presentation.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.challenge.foodappchallenge3.data.repository.CartRepository
import com.challenge.foodappchallenge3.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch

class CheckoutViewModel(private val cartRepository: CartRepository) : ViewModel() {
    val cartList = cartRepository.getCartList().asLiveData(Dispatchers.IO)
    fun clearCart(){
        viewModelScope.launch (Dispatchers.IO){
            cartRepository.deleteAll()
        }
    }

    private val _checkoutResult=MutableLiveData<ResultWrapper<Boolean>>()
        val checkoutResult: LiveData<ResultWrapper<Boolean>>
            get()=_checkoutResult

    fun order(){
        viewModelScope.launch(Dispatchers.IO) {
            val carts= cartList.value?.payload?.first ?:return@launch
            cartRepository.order(carts).collect{
                _checkoutResult.postValue(it)
            }
        }

    }

}

