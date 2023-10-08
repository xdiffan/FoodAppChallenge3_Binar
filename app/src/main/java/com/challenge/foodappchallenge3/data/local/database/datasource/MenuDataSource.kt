package com.challenge.foodappchallenge3.data.local.database.datasource
import com.challenge.foodappchallenge3.data.local.database.dao.MenuDao
import com.challenge.foodappchallenge3.data.local.database.entity.MenuEntity
import kotlinx.coroutines.flow.Flow

interface MenuDataSource {
    fun getAllMenus(): Flow<List<MenuEntity>>
    fun getMenuById(id: Int): Flow<MenuEntity>
    suspend fun insertMenus(product: List<MenuEntity>)
    suspend fun deleteMenu(product: MenuEntity): Int
    suspend fun updateMenu(product: MenuEntity): Int

}

class MenuDatabaseDataSource(private val dao : MenuDao) : MenuDataSource {
    override fun getAllMenus(): Flow<List<MenuEntity>> {
        return dao.getAllMenus()
    }

    override fun getMenuById(id: Int): Flow<MenuEntity> {
        return dao.getMenuById(id)
    }

    override suspend fun insertMenus(product: List<MenuEntity>) {
        return dao.insertMenu(product)
    }

    override suspend fun deleteMenu(product: MenuEntity): Int {
        return dao.deleteMenu(product)
    }

    override suspend fun updateMenu(product: MenuEntity): Int {
        return dao.updateMenu(product)
    }

}