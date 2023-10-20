package com.challenge.foodappchallenge3.data.network.api.datasource

import com.challenge.foodappchallenge3.data.network.api.model.category.CategoriesResponse
import com.challenge.foodappchallenge3.data.network.api.model.menu.MenusResponse
import com.challenge.foodappchallenge3.data.network.api.model.order.OrderRequest
import com.challenge.foodappchallenge3.data.network.api.model.order.OrderResponse
import com.challenge.foodappchallenge3.data.network.api.service.RestaurantService

interface RestaurantDataSource {
    suspend fun getMenus(category: String? = null) : MenusResponse
    suspend fun getCategories() : CategoriesResponse
    suspend fun createOrder(orderRequest: OrderRequest) : OrderResponse

}


class RestaurantApiDataSource(
    private val service: RestaurantService
): RestaurantDataSource {
    override suspend fun getMenus(category: String?): MenusResponse {
        return service.getMenus(category)
    }

    override suspend fun getCategories(): CategoriesResponse {
        return service.getCategories()
    }

    override suspend fun createOrder(orderRequest: OrderRequest): OrderResponse {
        return service.createOrder(orderRequest)
    }
}