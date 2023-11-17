package com.challenge.foodappchallenge3.data.repository

import android.net.Uri
import app.cash.turbine.test
import com.challenge.foodappchallenge3.data.network.firebase.auth.FirebaseAuthDataSource
import com.challenge.foodappchallenge3.model.User
import com.challenge.foodappchallenge3.utils.ResultWrapper
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {
    @MockK
    lateinit var firebaseDataSource: FirebaseAuthDataSource

    private lateinit var repository: UserRepository
    val mockUser = User(
        fullName = "Agus",
        email = "agus@gmail.com",
    )
    private val username = "test123"
    private val email = "test@test.com"
    private val password = "test1234"
    val photoUri = Uri.parse("test.com")

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = UserRepositoryImpl(firebaseDataSource)
    }

    @Test
    fun `doLogin loading`() {
        coEvery { firebaseDataSource.doLogin(any(), any()) } returns true
        runTest {
            repository.doLogin(email, password)
                .map {
                    delay(100)
                    it
                }.test {
                    delay(110)
                    val result = expectMostRecentItem()
                    assertTrue(result is ResultWrapper.Loading)
                    coVerify { firebaseDataSource.doLogin(any(), any()) }
                }
        }
    }

    @Test
    fun `doLogin success`() {
        coEvery { firebaseDataSource.doLogin(any(), any()) } returns true
        runTest {
            repository.doLogin(email, password)
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val result = expectMostRecentItem()
                    assertTrue(result is ResultWrapper.Success)
                   assertEquals(result.payload, true)
                    coVerify { firebaseDataSource.doLogin(any(), any()) }
                }
        }
    }
    @Test
    fun `doLogin error`() {
        coEvery { firebaseDataSource.doLogin(any(), any()) } throws IllegalStateException("Error login")
        runTest {
            repository.doLogin(email, password)
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val result = expectMostRecentItem()
                    assertTrue(result is ResultWrapper.Error)
                    coVerify { firebaseDataSource.doLogin(any(), any()) }
                }
        }
    }

    @Test
    fun `doRegister loading`() {
        coEvery { firebaseDataSource.doRegister(any(), any(), any()) } returns true
        runTest {
            repository.doRegister(username, email, password)
                .map {
                    delay(100)
                    it
                }.test {
                    delay(110)
                    val result = expectMostRecentItem()
                    assertTrue(result is ResultWrapper.Loading)
                    coVerify { firebaseDataSource.doRegister(any(), any(), any()) }
                }
        }
    }

    @Test
    fun `doRegister success`() {
        coEvery { firebaseDataSource.doRegister(any(), any(), any()) } returns true
        runTest {
            repository.doRegister(username, email, password)
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val result = expectMostRecentItem()
                    assertTrue(result is ResultWrapper.Success)
                    assertEquals(result.payload, true)
                    coVerify { firebaseDataSource.doRegister(any(), any(), any()) }
                }
        }
    }
    @Test
    fun `doRegister error`() {
        coEvery { firebaseDataSource.doRegister(any(), any(), any()) } throws IllegalStateException("register error")
        runTest {
            repository.doRegister(username, email, password)
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val result = expectMostRecentItem()
                    assertTrue(result is ResultWrapper.Error)
                    coVerify { firebaseDataSource.doRegister(any(), any(), any()) }
                }
        }
    }

    @Test
    fun isLoggedIn() {
        every { firebaseDataSource.isLoggedIn() } returns true
        val result = repository.isLoggedIn()
        verify { firebaseDataSource.isLoggedIn() }
        assertTrue(result)
    }

    @Test
    fun doLogout() {
        every { firebaseDataSource.doLogout() } returns true
        repository.doLogout()
        verify { firebaseDataSource.doLogout() }
    }

    @Test
    fun getCurrentUser() {
        val user: FirebaseUser = mockk()
        every { firebaseDataSource.getCurrentUser() } returns user

        every { user.displayName } returns mockUser.fullName
        every { user.email } returns mockUser.email

        val result = repository.getCurrentUser()
        verify { firebaseDataSource.getCurrentUser() }
        assertEquals(mockUser, result)
    }

    @Test
    fun `updateProfile loading`() {
        coEvery { firebaseDataSource.updateProfile(any(), any()) } returns true
        runTest {
            repository.updateProfile(username, photoUri)
                .map {
                    delay(100)
                    it
                }.test {
                    delay(110)
                    val result = expectMostRecentItem()
                    assertTrue(result is ResultWrapper.Loading)
                    coVerify { firebaseDataSource.updateProfile(any(), any()) }
                }
        }
    }

    @Test
    fun `updateProfile success`() {
        coEvery { firebaseDataSource.updateProfile(any(), any()) } returns true
        runTest {
            repository.updateProfile(username, photoUri)
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val result = expectMostRecentItem()
                    assertTrue(result is ResultWrapper.Success)
                    assertEquals(result.payload, true)
                    coVerify { firebaseDataSource.updateProfile(any(), any()) }
                }
        }
    }

    @Test
    fun sendChangePasswordRequestByEmail() {
        coEvery { repository.sendChangePasswordRequestByEmail() } returns true
        val result: Boolean = repository.sendChangePasswordRequestByEmail()
        assertTrue(result)

    }

    @Test
    fun `updatePassword  loading `() {
        coEvery { firebaseDataSource.updatePassword(any()) } returns true
        runTest {
            repository.updatePassword("new pass").map {
                delay(100)
                it
            }.test {
                delay(120)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { firebaseDataSource.updatePassword(any()) }
            }
        }
    }
    @Test
    fun `updatePassword success `() {
        coEvery { firebaseDataSource.updatePassword(any()) } returns true
        runTest {
            repository.updatePassword("new pass").map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { firebaseDataSource.updatePassword(any()) }
            }
        }
    }
    @Test
    fun `updatePassword error `() {
        coEvery { firebaseDataSource.updatePassword(any()) } throws IllegalStateException("fail update password")
        runTest {
            repository.updatePassword("new pass").map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { firebaseDataSource.updatePassword(any()) }
            }
        }
    }
    @Test
    fun `update email, result success`() {
        coEvery { firebaseDataSource.updateEmail(any()) } returns true
        runTest {
            repository.updateEmail("new email").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
               assertTrue(data is ResultWrapper.Success)
                coVerify { firebaseDataSource.updateEmail(any()) }
            }
        }
    }
    @Test
    fun `update email, result error`() {
        coEvery { firebaseDataSource.updateEmail(any()) } throws IllegalStateException("update fail")
        runTest {
            repository.updateEmail("new email").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
               assertTrue(data is ResultWrapper.Error)
                coVerify { firebaseDataSource.updateEmail(any()) }
            }
        }
    }
}
