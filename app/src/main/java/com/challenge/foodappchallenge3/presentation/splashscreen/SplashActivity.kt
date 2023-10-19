package com.challenge.foodappchallenge3.presentation.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.challenge.foodappchallenge3.data.network.firebase.auth.FirebaseAuthDataSource
import com.challenge.foodappchallenge3.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.challenge.foodappchallenge3.data.repository.UserRepository
import com.challenge.foodappchallenge3.data.repository.UserRepositoryImpl
import com.challenge.foodappchallenge3.databinding.ActivitySplashBinding
import com.challenge.foodappchallenge3.presentation.login.LoginActivity
import com.challenge.foodappchallenge3.presentation.main.MainActivity
import com.challenge.foodappchallenge3.utils.GenericViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels {
        GenericViewModelFactory.create(createViewModel())
    }

    private fun createViewModel(): SplashViewModel {
        val firebaseAuth = FirebaseAuth.getInstance()
        val dataSource: FirebaseAuthDataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
        val repository: UserRepository = UserRepositoryImpl(dataSource)
        return SplashViewModel(repository)
    }

    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        checkIfUserLogin()
    }

    private fun checkIfUserLogin() {
        lifecycleScope.launch {
            delay(3000)
            if (viewModel.isUserLoggedIn()){
                navigateToMain()
            } else{
                navigateToLogin()
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }
}