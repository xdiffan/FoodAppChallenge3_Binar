package com.challenge.foodappchallenge3.data.repository

import com.challenge.foodappchallenge3.data.dummy.DummyCategoryDataSource
import com.challenge.foodappchallenge3.data.local.database.datasource.MenuDataSource
import com.challenge.foodappchallenge3.data.local.database.mapper.toMenuList
import com.challenge.foodappchallenge3.model.Category
import com.challenge.foodappchallenge3.model.Menu
import com.challenge.foodappchallenge3.utils.ResultWrapper
import com.challenge.foodappchallenge3.utils.proceed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


interface MenuRepository {
    fun getCategories(): List<Category>
    fun getMenus(): Flow<ResultWrapper<List<Menu>>>
}

class MenuRepositoryImplementation(
    private val menuDataSource: MenuDataSource,
    private val dummyCategoryDataSource: DummyCategoryDataSource
) : MenuRepository {

    override fun getCategories(): List<Category> {
        return dummyCategoryDataSource.getCategoryData()
    }

    override fun getMenus(): Flow<ResultWrapper<List<Menu>>> {
        return menuDataSource.getAllMenus().map { proceed { it.toMenuList() } }
    }
}