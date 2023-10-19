package com.challenge.foodappchallenge3.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.challenge.foodappchallenge3.R
import com.challenge.foodappchallenge3.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.challenge.foodappchallenge3.data.repository.UserRepositoryImpl
import com.challenge.foodappchallenge3.databinding.FragmentProfileBinding
import com.challenge.foodappchallenge3.presentation.login.LoginActivity
import com.challenge.foodappchallenge3.utils.GenericViewModelFactory
import com.challenge.foodappchallenge3.utils.proceedWhen
import com.google.firebase.auth.FirebaseAuth

class FragmentProfile : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels {
        GenericViewModelFactory.create(createViewModel())
    }

    private fun createViewModel(): ProfileViewModel {
        val firebaseAuth = FirebaseAuth.getInstance()
        val dataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
        val repo = UserRepositoryImpl(dataSource)
        return ProfileViewModel(repo)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )
        setupForm()
        showUserData()
        setClickListeners()
        observeData()
    }

    private fun observeData() {
        viewModel.updateProfileResult.observe(viewLifecycleOwner){
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbChangeProfileLoading.isVisible = false
                    binding.btnChangeProfile.isVisible = true
                    binding.btnChangeProfile.isEnabled = true
                },
                doOnLoading = {
                    binding.pbChangeProfileLoading.isVisible = true
                    binding.btnChangeProfile.isVisible = false
                },
                doOnError = {
                    binding.pbChangeProfileLoading.isVisible = false
                    binding.btnChangeProfile.isVisible = true
                    binding.btnChangeProfile.isEnabled = true
                    Toast.makeText(
                        requireContext(),
                        "Change Profile Failed: ${it.exception?.message.orEmpty()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    private fun setClickListeners() {
        binding.btnChangeProfile.setOnClickListener{
            changeProfileData()
        }
        binding.btnChangePassword.setOnClickListener {
            requestChangePassword()
        }
        binding.btnLogout.setOnClickListener {
            doLogout()
        }
    }

    private fun doLogout() {
        AlertDialog.Builder(requireContext())
            .setMessage("Apakah kamu ingin keluar? " )

            .setPositiveButton("Ya"){_,_ ->
                viewModel.doLogout()
                navigateToLogin()
            }.setNegativeButton("Tidak"){_,_ ->

            }.create().show()
    }

    private fun navigateToLogin() {
        requireActivity().run{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun requestChangePassword() {
        viewModel.createChangePasswordReq()
        AlertDialog.Builder(requireContext())
            .setMessage("Permintaan ubah password telah dikirim ke email: " +
                    "${viewModel.getCurrentUser()?.email}")
            .setPositiveButton("Okay"){_,_ ->

            }.create().show()
    }

    private fun changeProfileData() {
        val fullName = binding.layoutForm.etName.text.toString().trim()
        if (isFormValid()){
            AlertDialog.Builder(requireContext())
                .setMessage("Apakah kamu ingin mengganti nama ? " )

                .setPositiveButton("Ya"){_,_ ->
                    viewModel.updateProfile(fullName)
                }.setNegativeButton("Tidak"){_,_ ->

                }.create().show()
        }
    }

    private fun isFormValid(): Boolean {
        val currentName = viewModel.getCurrentUser()?.fullName
        val newName = binding.layoutForm.etName.text.toString().trim()
        return checkNameValidation(currentName,newName)
    }

    private fun checkNameValidation(
        currentName: String?,
        newName: String
    ): Boolean {
        return if(newName.isEmpty()){
            binding.layoutForm.tilName.isErrorEnabled = true
            binding.layoutForm.tilName.error = getString(R.string.error_empty)
            false
        } else if (newName == currentName){
            binding.layoutForm.tilName.isErrorEnabled = true
            binding.layoutForm.tilName.error = getString(R.string.error_empty)
            false
        } else{
            binding.layoutForm.tilName.isErrorEnabled = false
            true
        }
    }

    private fun showUserData() {
        binding.layoutForm.etName.setText(viewModel.getCurrentUser()?.fullName)
        binding.layoutForm.etEmail.setText(viewModel.getCurrentUser()?.email)
    }

    private fun setupForm() {
        binding.layoutForm.tilName.isVisible = true
        binding.layoutForm.tilEmail.isVisible = true
        binding.layoutForm.etEmail.isEnabled = false
    }
}