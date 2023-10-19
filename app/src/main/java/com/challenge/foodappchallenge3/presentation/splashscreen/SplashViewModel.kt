package com.challenge.foodappchallenge3.presentation.splashscreen

import androidx.lifecycle.ViewModel
import com.challenge.foodappchallenge3.data.repository.UserRepository

class SplashViewModel(private val repository: UserRepository) : ViewModel() {
    fun isUserLoggedIn() = repository.isLoggedIn()

}