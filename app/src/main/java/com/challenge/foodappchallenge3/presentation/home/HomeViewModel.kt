package com.challenge.foodappchallenge3.presentation.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.challenge.foodappchallenge3.data.repository.MenuRepository
import com.challenge.foodappchallenge3.data.repository.UserRepository
import com.challenge.foodappchallenge3.model.Category
import com.challenge.foodappchallenge3.model.Menu

import com.challenge.foodappchallenge3.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MenuRepository,private val profileRepository: UserRepository) : ViewModel() {
    private val _categories = MutableLiveData<ResultWrapper<List<Category>>>()
    val categories : LiveData<ResultWrapper<List<Category>>>
        get() = _categories

    private val _menus = MutableLiveData<ResultWrapper<List<Menu>>>()
    val menus : LiveData<ResultWrapper<List<Menu>>>
        get() = _menus

    fun getCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategories().collect{
                _categories.postValue(it)
            }
        }
    }

    fun getCurrentUser() = profileRepository.getCurrentUser()

    fun getMenus(category: String? = null){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMenus(if(category == "all") null else category).collect{
                _menus.postValue(it)
            }
        }
    }

}