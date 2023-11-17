package com.challenge.foodappchallenge3.data.network.firebase.auth

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FirebaseAuthDataSourceImplTest {
    @MockK
    lateinit var firebaseAuth: FirebaseAuth

    private lateinit var firebaseAuthDataSource: FirebaseAuthDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        firebaseAuthDataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
    }

    private fun mockTaskVoid(exception: Exception? = null): Task<Void> {
        val task: Task<Void> = mockk(relaxed = true)
        every { task.isCanceled } returns false
        every { task.exception } returns exception
        every { task.isComplete } returns true

        val relaxedVoid = mockk<Void>(relaxed = true)
        every { task.result } returns relaxedVoid
        return task
    }

    private fun mockTaskAuthResult(exception: Exception? = null): Task<AuthResult> {
        val task: Task<AuthResult> = mockk(relaxed = true)
        every { task.isCanceled } returns false
        every { task.exception } returns exception
        every { task.isComplete } returns true

        val relaxedResult = mockk<AuthResult>(relaxed = true)
        every { task.result } returns relaxedResult
        every { task.result.user } returns mockk(relaxed = true)
        return task
    }


    @Test
    fun `test do login`() {
        coEvery {
            firebaseAuth.signInWithEmailAndPassword(
                any(),
                any()
            )
        } returns mockTaskAuthResult()
        runTest {
            val result = firebaseAuthDataSource.doLogin("email", "password")
            assertEquals(result, true)
        }
    }

//    @Test
//    fun `test do register`() {
//        coEvery {
//            firebaseAuth.createUserWithEmailAndPassword(
//                any(),
//                any()
//            )
//        } returns mockTaskAuthResult()
//        runTest {
//            val result =
//                firebaseAuthDataSource.doRegister("email", "bambang@gmail.com", "bambangGamteng")
//            assertEquals(result, true)
//        }
//    }

    @Test
    fun getCurrentUser() {
        val mockUser = mockk<FirebaseUser>()
        every { firebaseAuth.currentUser } returns mockUser
        val result = firebaseAuthDataSource.getCurrentUser()
        verify { firebaseAuth.currentUser }
        assertEquals(mockUser, result)
    }

    @Test
    fun isLoggedIn() {
        every { firebaseAuth.currentUser } returns mockk()
        val result = firebaseAuthDataSource.isLoggedIn()
        assertTrue(result)

    }

    @Test
    fun `test updatePassword`() = runTest {
        coEvery { firebaseAuth.currentUser?.updatePassword(any()) } returns mockTaskVoid()
        val result = firebaseAuthDataSource.updatePassword("passwordANyar")
        assertTrue(result)

    }

    @Test
    fun `test updateEmail`() = runTest {
        coEvery { firebaseAuth.currentUser?.updateEmail(any()) } returns mockTaskVoid()
        val result = firebaseAuthDataSource.updateEmail("newEmail@example.com")
        assertTrue(result)
    }

    @Test
    fun `test sendChangePasswordRequestByEmail`() {
        mockkObject(firebaseAuth)
        coEvery { firebaseAuth.currentUser?.email } returns "user@example.com"
        coEvery { firebaseAuth.sendPasswordResetEmail("user@example.com") } returns mockTaskVoid()

        val result = firebaseAuthDataSource.sendChangePasswordRequestByEmail()
        assertTrue(result)

    }
    @Test
    fun `test updateProfile`() = runTest {
        val mockCurrentUser: FirebaseUser = mockk(relaxed = true)
        coEvery { firebaseAuth.currentUser } returns mockCurrentUser
        coEvery { mockCurrentUser.updateProfile(any()) } returns mockTaskVoid()

        val result = firebaseAuthDataSource.updateProfile("John Doe", Uri.parse("content://path/to/photo"))
        assertTrue(result)
    }
    @Test
    fun testLogout(){
        mockkStatic(FirebaseAuth::class)
        every { FirebaseAuth.getInstance() }returns firebaseAuth
        every { firebaseAuth.signOut() }returns Unit
        val result=firebaseAuthDataSource.doLogout()
        assertEquals(result,true)
    }
}