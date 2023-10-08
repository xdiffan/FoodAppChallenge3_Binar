package com.challenge.foodappchallenge3.presentation.checkout

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.challenge.foodappchallenge3.R
import com.challenge.foodappchallenge3.data.local.database.AppDatabase
import com.challenge.foodappchallenge3.data.local.database.datasource.CartDataSource
import com.challenge.foodappchallenge3.data.local.database.datasource.CartDatabaseDataSource
import com.challenge.foodappchallenge3.data.repository.CartRepository
import com.challenge.foodappchallenge3.data.repository.CartRepositoryImpl
import com.challenge.foodappchallenge3.databinding.ActivityCheckoutBinding
import com.challenge.foodappchallenge3.presentation.cart.CartListAdapter
import com.challenge.foodappchallenge3.utils.GenericViewModelFactory
import com.challenge.foodappchallenge3.utils.proceedWhen
import com.challenge.foodappchallenge3.utils.toCurrencyFormat

class CheckoutActivity : AppCompatActivity() {

    private val viewModel: CheckoutViewModel by viewModels {
        val database =
            AppDatabase.getInstance(this)
        val cartDao = database.cartDao()
        val cartDataSource: CartDataSource =
            CartDatabaseDataSource(cartDao)
        val repo: CartRepository =
            CartRepositoryImpl(cartDataSource)
        GenericViewModelFactory.create(
            CheckoutViewModel(repo)
        )
    }

    private val binding: ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(
            layoutInflater
        )
    }

    private val adapter: CartListAdapter by lazy {
        CartListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupList()
        observeData()
        setClickListener()
    }
    private fun setClickListener(){
        binding.btnOrder.setOnClickListener {
            dialogOrder()
            viewModel.deleteAll()
        }
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun dialogOrder() {
        AlertDialog.Builder(this)
            .setTitle("Order Berhasil")
            .setPositiveButton("Kembali ke Keranjang") { _, _ -> finish() }
            .show()
    }



    private fun setupList() {
        binding.rvCheckout.adapter = adapter
    }

    private fun observeData() {
        viewModel.cartList.observe(this) {
            it.proceedWhen(
                doOnSuccess = { result ->
                    binding.layoutState.root.isVisible =
                        false
                    binding.layoutState.pbLoading.isVisible =
                        false
                    binding.layoutState.tvError.isVisible =
                        false
                    binding.svCheckoutController.isVisible =
                        true
                    result.payload?.let { (carts, totalPrice) ->
                        adapter.submitData(carts)
                        binding.totalPembayaran.text = totalPrice.toCurrencyFormat()
                    }
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible =
                        true
                    binding.layoutState.pbLoading.isVisible =
                        true
                    binding.layoutState.tvError.isVisible =
                        false
                    binding.svCheckoutController.isVisible =
                        false
                },
                doOnEmpty = { _ ->
                    binding.layoutState.root.isVisible =
                        true
                    binding.layoutState.pbLoading.isVisible =
                        false
                    binding.layoutState.tvError.isVisible =
                        true
                    binding.layoutState.tvError.text =
                        getString(R.string.error_empty)
                    binding.btnOrder.isClickable =
                        false
                },
                doOnError = { err ->
                    binding.layoutState.root.isVisible =
                        true
                    binding.layoutState.pbLoading.isVisible =
                        false
                    binding.layoutState.tvError.isVisible =
                        true
                    binding.layoutState.tvError.text =
                        err.exception?.message.orEmpty()
                    binding.svCheckoutController.isVisible =
                        false
                    binding.btnOrder.isClickable =
                        false
                }
            )
        }
    }
}