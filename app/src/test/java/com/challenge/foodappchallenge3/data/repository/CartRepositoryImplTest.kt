package com.challenge.foodappchallenge3.data.repository

import app.cash.turbine.test
import com.challenge.foodappchallenge3.data.local.database.datasource.CartDataSource
import com.challenge.foodappchallenge3.data.local.database.entity.CartEntity
import com.challenge.foodappchallenge3.data.local.database.mapper.toCartEntity
import com.challenge.foodappchallenge3.data.network.api.datasource.RestaurantDataSource
import com.challenge.foodappchallenge3.data.network.api.model.order.OrderResponse
import com.challenge.foodappchallenge3.model.Cart
import com.challenge.foodappchallenge3.model.Menu
import com.challenge.foodappchallenge3.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.lang.IllegalStateException

class CartRepositoryImplTest {

    @MockK
    lateinit var localDataSource: CartDataSource

    @MockK
    lateinit var remoteDataSource: RestaurantDataSource

    private lateinit var repository: CartRepository
    private val fakeCartList = listOf(
        CartEntity(
            id = 1,
            menuId = 1,
            menuName = "Sate Cirebon",
            menuPrice = 12000.0,
            menuImgUrl = "url",
            itemQuantity = 2,
            itemNotes = "notes"
        ),
        CartEntity(
            id = 2,
            menuId = 1,
            menuName = "Sate Padang",
            menuPrice = 14000.0,
            menuImgUrl = "url",
            itemQuantity = 2,
            itemNotes = "notes"
        )
    )

    private val mockCart = Cart(
        id = 1,
        menuId = 1,
        menuName = "Sate",
        menuPrice = 12000.0,
        menuImgUrl = "url",
        itemQuantity = 0,
        itemNotes = "notes"
    )


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CartRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Test
    fun deleteAll() {
        coEvery { localDataSource.deleteAll() } returns Unit
        runTest {
            val result = repository.deleteAll()
            coVerify { localDataSource.deleteAll() }
            assertEquals(result, Unit)
        }
    }

    @Test
    fun `get user cart data, result success`() {
        val flowMock = flow {
            emit(fakeCartList)
        }
        every { localDataSource.getAllCarts() } returns flowMock
        runTest {
            repository.getCartList().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                verify { localDataSource.getAllCarts() }
                assertEquals(data.payload?.first?.size, 2)
                assertEquals(data.payload?.second, 52000.0)
            }
        }
    }


    @Test
    fun `get user cart data, result loading`() {
        every { localDataSource.getAllCarts() } returns flow {
            emit(fakeCartList)
        }
        runTest {
            repository.getCartList().map {
                delay(100)
                it
            }.test {
                delay(2101)
                val data = expectMostRecentItem()
                verify { localDataSource.getAllCarts() }
                assertTrue(data is ResultWrapper.Loading)
            }
        }
    }

    @Test
    fun `get user cart data, result error`() {
        every { localDataSource.getAllCarts() } returns flow {
            throw IllegalStateException("Mock Errror")
        }
        runTest {
            repository.getCartList().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                verify { localDataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `get user cart data, result empty`() {
        every { localDataSource.getAllCarts() } returns flow {
            emit(listOf())
        }
        runTest {
            repository.getCartList().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                verify { localDataSource.getAllCarts() }
                assertTrue(data is ResultWrapper.Empty)
                assertTrue(data.payload?.first?.isEmpty() == true)
            }
        }
    }

    @Test
    fun `create cart loading menu id not null`() {
        runTest {
            val mockMenu = mockk<Menu>(relaxed = true)
            coEvery { localDataSource.insertCart(any()) } returns 1
            repository.createCart(mockMenu, 1)
                .map {
                    delay(100)
                    it
                }.test {
                    delay(110)
                    val result = expectMostRecentItem()
                    assertTrue(result is ResultWrapper.Loading)
                    coVerify { localDataSource.insertCart(any()) }
                }
        }
    }
    @Test
    fun `create cart sucess menu id not null`() {
        runTest {
            val mockMenu = mockk<Menu>(relaxed = true)
            coEvery { localDataSource.insertCart(any()) } returns 1
            repository.createCart(mockMenu, 1)
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val result = expectMostRecentItem()
                    assertTrue(result is ResultWrapper.Success)
                    assertEquals(result.payload,true)
                    coVerify { localDataSource.insertCart(any()) }
                }
        }
    }
    @Test
    fun `create cart error menu id not null`() {
        runTest {
            val mockMenu = mockk<Menu>(relaxed = true)
            coEvery { localDataSource.insertCart(any()) } throws IllegalStateException("Mock Errror")
            repository.createCart(mockMenu, 1)
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val result = expectMostRecentItem()
                    assertTrue(result is ResultWrapper.Error)
                    coVerify { localDataSource.insertCart(any()) }
                }
        }
    }


    @Test
    fun `create cart error with null id`() {
        val mockMenu =mockk<Menu>(relaxed = true){
            every { id }returns null
        }
        val fakeQuantity = 2
        runTest {
            repository.createCart(mockMenu, fakeQuantity).collect{result->
                assertTrue(result is ResultWrapper.Error)

                // Verify that insertCart was not called
                coVerify(exactly = 0) { localDataSource.insertCart(any()) }
            }
        }
    }

        @Test
        fun `decrease cart when quantity less than or equal 0`() {
            coEvery { localDataSource.deleteCart(any()) } returns 1
            coEvery { localDataSource.updateCart(any()) } returns 1
            runTest {
                repository.decreaseCart(mockCart).map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val result = expectMostRecentItem()
                    assertEquals(result.payload, true)
                    coVerify(atLeast = 1) { localDataSource.deleteCart(any()) }
                    coVerify(atLeast = 0) { localDataSource.updateCart(any()) }
                }
            }
        }

        @Test
        fun `decrease cart when quantity more than  0`() {
            val mockCart = Cart(
                id = 1,
                menuId = 1,
                menuName = "Sate",
                menuPrice = 12000.0,
                menuImgUrl = "url",
                itemQuantity = 2,
                itemNotes = "notes"
            )
            coEvery { localDataSource.deleteCart(any()) } returns 1
            coEvery { localDataSource.updateCart(any()) } returns 1
            runTest {
                repository.decreaseCart(mockCart).map {
                    delay(100)
                    it
                }.test {
                    delay(2201)
                    val result = expectMostRecentItem()
                    assertEquals(result.payload, true)
                    coVerify(atLeast = 0) { localDataSource.deleteCart(any()) }
                    coVerify(atLeast = 1) { localDataSource.updateCart(any()) }
                }
            }
        }

        @Test
        fun `increase cart`() {
            runTest {
                coEvery { localDataSource.updateCart(any()) } returns 1
                repository.increaseCart(mockCart).test {
                    coVerify { localDataSource.updateCart(any()) }
                    val result = expectMostRecentItem()
                    assertTrue(result is ResultWrapper.Success)

                }
            }
        }

        @Test
        fun `set cart notes`() {
            coEvery { localDataSource.updateCart(any()) } returns 1
            runTest {
                repository.setCartNotes(mockCart).map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val result = expectMostRecentItem()
                    assertEquals(result.payload, true)
                    coVerify(atLeast = 0) { localDataSource.updateCart(any()) }
                }
            }
        }

        @Test
        fun `delete cart success`() {
            coEvery { localDataSource.deleteCart(mockCart.toCartEntity()) } returns 1
            runTest {
                repository.deleteCart(mockCart).test {
                    val result = expectMostRecentItem()
                    coVerify { localDataSource.deleteCart(any()) }
                    assertTrue(result is ResultWrapper.Success)
                }
            }
        }

        @Test
        fun `create order success`() {
            runTest {
                val mockCarts = listOf(mockCart)
                coEvery { remoteDataSource.createOrder(any()) } returns OrderResponse(
                    code = 200,
                    message = "Success",
                    status = true
                )
                repository.order(mockCarts).map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val result = expectMostRecentItem()
                    assertTrue(result is ResultWrapper.Success)
                }
            }
        }
    @Test
    fun `create order error `(){
        val fakeListCart= listOf(mockCart)
        runTest {
            coEvery { remoteDataSource.createOrder(any()) }throws IllegalStateException("error")
            repository.order(fakeListCart).test {
                coVerify { remoteDataSource.createOrder(any()) }
                val result=expectMostRecentItem()
                assertTrue(result is ResultWrapper.Error)
            }
        }

    }
    }