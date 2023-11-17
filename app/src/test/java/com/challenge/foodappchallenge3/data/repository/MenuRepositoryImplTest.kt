package com.challenge.foodappchallenge3.data.repository

import app.cash.turbine.test
import com.challenge.foodappchallenge3.data.network.api.datasource.RestaurantDataSource
import com.challenge.foodappchallenge3.data.network.api.model.category.CategoriesResponse
import com.challenge.foodappchallenge3.data.network.api.model.category.CategoryResponse
import com.challenge.foodappchallenge3.data.network.api.model.menu.MenuItemResponse
import com.challenge.foodappchallenge3.data.network.api.model.menu.MenusResponse
import com.challenge.foodappchallenge3.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import java.lang.IllegalStateException

class MenuRepositoryImplTest {

    @MockK
    lateinit var remoteDataSource: RestaurantDataSource

    private lateinit var repository: MenuRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = MenuRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `get categories, with result loading`() {
        val mockCategoryResponse = mockk<CategoriesResponse>()
        runTest {
            coEvery { remoteDataSource.getCategories() } returns mockCategoryResponse
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { remoteDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories, with result success`() {
        val fakeCategoryResponse = CategoryResponse(
            name = "Apa",
            imageUrl = "apa"
        )
        val fakeCategoriesReponse = CategoriesResponse(
            code = 200,
            status = true,
            message = "Success",
            data = listOf(fakeCategoryResponse)
        )
        runTest {
            coEvery { remoteDataSource.getCategories() } returns fakeCategoriesReponse
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 1)
                coVerify { remoteDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories, with result empty`() {
        val fakeCategoriesReponse = CategoriesResponse(
            code = 200,
            status = true,
            message = "Success but empty",
            data = emptyList()
        )
        runTest {
            coEvery { remoteDataSource.getCategories() } returns fakeCategoriesReponse
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { remoteDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories, with result error`() {
        runTest {
            coEvery { remoteDataSource.getCategories() } throws IllegalStateException("Mock Error")
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { remoteDataSource.getCategories() }
            }
        }
    }

    @Test
    fun`get menus, with result loading`(){
        val mockMenusResponse=mockk<MenusResponse>()
        runTest {
            coEvery { remoteDataSource.getMenus(any()) }returns mockMenusResponse
            repository.getMenus("makanan").map {
                delay(100)
                it
            }.test {
                delay(110)
                val data=expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { remoteDataSource.getMenus(any()) }
            }
        }
    }
    @Test
    fun`get menus, with result success`(){
        val fakeMenuItemResponse=MenuItemResponse(
            id = 1,
            restaurantAddress = "jember",
            description = "apa",
            price = 12000.0,
            formattedPrice = "Rp5000",
            imageUrl = "Www",
            name = "W"
        )
        val fakeMenusResponse=MenusResponse(
            code = 200,
            status = true,
            message = "Success",
            data = listOf(fakeMenuItemResponse)
        )
        runTest {
            coEvery { remoteDataSource.getMenus(any()) }returns fakeMenusResponse
            repository.getMenus("makanan").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data=expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 1)
                assertEquals(data.payload?.get(0)?.id, 1)
                coVerify { remoteDataSource.getMenus(any()) }
            }
        }
    }
    @Test
    fun`get menus, with result empty`(){
        val fakeMenusResponse=MenusResponse(
            code = 200,
            status = true,
            message = "Success",
            data = emptyList()
        )
        runTest {
            coEvery { remoteDataSource.getMenus(any()) }returns fakeMenusResponse
            repository.getMenus().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data=expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { remoteDataSource.getMenus(any()) }
            }
        }
    }
    @Test
    fun`get menus, with result error`(){
        runTest {
            coEvery { remoteDataSource.getMenus(any()) } throws IllegalStateException("Mock Error")
            repository.getMenus("makanan").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data=expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { remoteDataSource.getMenus(any()) }
            }
        }
    }




}