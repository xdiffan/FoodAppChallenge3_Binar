package com.challenge.foodappchallenge3.presentation.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.challenge.foodappchallenge3.R
import com.challenge.foodappchallenge3.data.network.firebase.auth.FirebaseAuthDataSource
import com.challenge.foodappchallenge3.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.challenge.foodappchallenge3.data.repository.UserRepository
import com.challenge.foodappchallenge3.data.repository.UserRepositoryImpl
import com.challenge.foodappchallenge3.databinding.ActivityRegisterBinding
import com.challenge.foodappchallenge3.presentation.login.LoginActivity
import com.challenge.foodappchallenge3.presentation.main.MainActivity
import com.challenge.foodappchallenge3.utils.GenericViewModelFactory
import com.challenge.foodappchallenge3.utils.proceedWhen
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }
    private val viewModel: RegisterViewModel by viewModels {
        GenericViewModelFactory.create(createViewModel())
    }

    private fun createViewModel(): RegisterViewModel {
        val firebaseAuth = FirebaseAuth.getInstance()
        val dataSource: FirebaseAuthDataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
        val repository: UserRepository = UserRepositoryImpl(dataSource)
        return RegisterViewModel(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupForm()
        setClickListeners()
        observeResult()
    }

    private fun setClickListeners() {
        binding.tvNavToLogin.setOnClickListener{
            navigateToLogin()
        }


        binding.btnRegister.setOnClickListener {
            doRegister()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
    }

    private fun doRegister() {
        if(isFormValid()){
            val name = binding.layoutForm.etName.text.toString().trim()
            val email = binding.layoutForm.etEmail.text.toString().trim()
            val password = binding.layoutForm.etPassword.text.toString().trim()
            viewModel.doRegister(name, email, password)
        }
    }

    private fun observeResult() {
        viewModel.registerResult.observe(this){
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnRegister.isVisible = true
                    binding.btnRegister.isEnabled = false
                    navigateToMain()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnRegister.isVisible = false
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnRegister.isVisible = true
                    binding.btnRegister.isEnabled = true
                    Toast.makeText(
                        this,
                        "Register Failed: ${it.exception?.message.orEmpty()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    private fun navigateToMain() {

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }


    private fun setupForm() {
        binding.layoutForm.tilName.isVisible = true
        binding.layoutForm.tilEmail.isVisible = true
        binding.layoutForm.tilPassword.isVisible = true
        binding.layoutForm.tilConfirmPassword.isVisible = true
    }

    private fun isFormValid(): Boolean {
        val name = binding.layoutForm.etName.text.toString().trim()
        val email = binding.layoutForm.etEmail.text.toString().trim()
        val password = binding.layoutForm.etPassword.text.toString().trim()
        val confirmPassword = binding.layoutForm.etConfirmPassword.text.toString().trim()

        return checkNameValidation(name) && checkEmailValidation(email)
                && checkPasswordValidation(password, binding.layoutForm.tilPassword)
                && checkPasswordValidation(confirmPassword, binding.layoutForm.tilConfirmPassword)
                && checkPwdAndConfirmPwd(password, confirmPassword)
    }

    private fun checkNameValidation(fullName: String): Boolean {
        return if (fullName.isEmpty()){
            binding.layoutForm.tilName.isErrorEnabled = true
            binding.layoutForm.tilName.error = getString(R.string.txt_error_email_empty)
            false
        } else{
            binding.layoutForm.tilName.isErrorEnabled = false
            true
        }
    }

    private fun checkEmailValidation(email: String): Boolean {
        return if (email.isEmpty()){
            //email cannot be empty
            binding.layoutForm.tilEmail.isErrorEnabled = true
            binding.layoutForm.tilEmail.error = getString(R.string.txt_error_email_empty)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //email format is correct
            binding.layoutForm.tilEmail.isErrorEnabled = true
            binding.layoutForm.tilEmail.error = getString(R.string.txt_error_email_invalid)
            false
        } else{
            binding.layoutForm.tilEmail.isErrorEnabled = false
            true
        }
    }

    private fun checkPasswordValidation(
        password: String,
        textInputLayout: TextInputLayout
    ): Boolean {
        return if (password.isEmpty()){
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = getString(R.string.txt_error_password_empty)
            false
        } else if (password.length < 8){
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = getString(R.string.txt_error_password_less_than_8_char)
            false
        } else{
            textInputLayout.isErrorEnabled = false
            true
        }
    }

    private fun checkPwdAndConfirmPwd(password: String, confirmPassword: String): Boolean {
        return if (password != confirmPassword){
            binding.layoutForm.tilPassword.isErrorEnabled = true
            binding.layoutForm.tilConfirmPassword.isErrorEnabled = true
            binding.layoutForm.tilPassword.error = getString(R.string.txt_password_not_matches)
            binding.layoutForm.tilConfirmPassword.error = getString(R.string.txt_register)
            false
        } else{
            binding.layoutForm.tilPassword.isErrorEnabled = false
            binding.layoutForm.tilConfirmPassword.isErrorEnabled = false
            true
        }
    }
}