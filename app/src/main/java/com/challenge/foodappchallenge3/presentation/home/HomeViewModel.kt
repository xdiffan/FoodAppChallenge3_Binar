package com.challenge.foodappchallenge3.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.challenge.foodappchallenge3.data.repository.MenuRepository
import com.challenge.foodappchallenge3.model.Menu
import com.challenge.foodappchallenge3.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val repo: MenuRepository) : ViewModel() {
    val menuData : LiveData<ResultWrapper<List<Menu>>>
        get() = repo.getMenus().asLiveData(Dispatchers.IO)

}

