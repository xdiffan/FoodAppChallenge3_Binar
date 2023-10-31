package com.challenge.foodappchallenge3.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.foodappchallenge3.data.repository.UserRepository
import com.challenge.foodappchallenge3.utils.ResultWrapper
import kotlinx.coroutines.launch

class RegisterViewModel(private val repo: UserRepository) : ViewModel() {

    private val _registerResult = MutableLiveData<ResultWrapper<Boolean>>()
    val registerResult: LiveData<ResultWrapper<Boolean>>
        get() = _registerResult

    fun doRegister(fullName: String, email: String, password: String) {
        viewModelScope.launch {
            repo.doRegister(fullName, email, password).collect { result ->
                _registerResult.postValue(result)
            }
        }
    }
}
