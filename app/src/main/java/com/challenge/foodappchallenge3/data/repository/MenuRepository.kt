package com.challenge.foodappchallenge3.data.repository


import com.challenge.foodappchallenge3.data.network.api.datasource.RestaurantDataSource
import com.challenge.foodappchallenge3.data.network.api.model.category.toCategoryList
import com.challenge.foodappchallenge3.data.network.api.model.menu.toMenuList
import com.challenge.foodappchallenge3.model.Category
import com.challenge.foodappchallenge3.model.Menu
import com.challenge.foodappchallenge3.utils.ResultWrapper

import com.challenge.foodappchallenge3.utils.proceedFlow
import kotlinx.coroutines.flow.Flow



interface MenuRepository {
    fun getCategories(): Flow<ResultWrapper<List<Category>>>
    fun getMenus(category: String? = null): Flow<ResultWrapper<List<Menu>>>
}

class MenuRepositoryImpl(
    private val apiDataSource: RestaurantDataSource
) : MenuRepository {
    override fun getCategories(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow {
            apiDataSource.getCategories().data?.toCategoryList() ?: emptyList()
        }
    }

    override fun getMenus(category: String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow {
            apiDataSource.getMenus(category).data?.toMenuList() ?: emptyList()
        }
    }

}