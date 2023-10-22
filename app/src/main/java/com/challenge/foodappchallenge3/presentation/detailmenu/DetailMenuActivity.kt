package com.challenge.foodappchallenge3.presentation.detailmenu

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import coil.load
import com.challenge.foodappchallenge3.R
import com.challenge.foodappchallenge3.data.local.database.AppDatabase
import com.challenge.foodappchallenge3.data.local.database.datasource.CartDataSource
import com.challenge.foodappchallenge3.data.local.database.datasource.CartDatabaseDataSource
import com.challenge.foodappchallenge3.data.network.api.datasource.RestaurantApiDataSource
import com.challenge.foodappchallenge3.data.network.api.service.RestaurantService
import com.challenge.foodappchallenge3.data.repository.CartRepository
import com.challenge.foodappchallenge3.data.repository.CartRepositoryImpl
import com.challenge.foodappchallenge3.databinding.ActivityDetailMenuBinding
import com.challenge.foodappchallenge3.model.Menu
import com.challenge.foodappchallenge3.utils.GenericViewModelFactory
import com.challenge.foodappchallenge3.utils.proceedWhen
import com.challenge.foodappchallenge3.utils.toCurrencyFormat

class DetailMenuActivity : AppCompatActivity() {
    private val binding: ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(
            layoutInflater
        )
    }
    private val viewModel: DetailMenuViewModel by viewModels {

        val database = AppDatabase.getInstance(this)
        val cartDao = database.cartDao()
        val cartDataSource: CartDataSource = CartDatabaseDataSource(cartDao)

        val service= RestaurantService.invoke()
        val restaurantApiDataSource= RestaurantApiDataSource(service)
        val repo: CartRepository = CartRepositoryImpl(cartDataSource,restaurantApiDataSource)
        GenericViewModelFactory.create(DetailMenuViewModel(intent?.extras,repo))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindMenu(viewModel.menu)
        observeData()
        setClickListener()
    }

    private fun setClickListener() {
        binding.cvLocation.setOnClickListener {
            navigateToGoogleMaps()
        }
        binding.icAdd.setOnClickListener {
            viewModel.add()
        }
        binding.icMinus.setOnClickListener {
            viewModel.minus()
        }
        binding.ibArrowBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnAddToCart.setOnClickListener{
            viewModel.addToCart()
        }
    }

    private fun observeData() {
        viewModel.priceLiveData.observe(this) {
            binding.btnAddToCart.text = it.toCurrencyFormat()
        }
        viewModel.menuCountLiveData.observe(this) {
            binding.tvCounting.text = it.toString()
        }

        viewModel.addToCartResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(this, "Pesanan Ditambahkan ke Keranjang !", Toast.LENGTH_SHORT).show()
                    finish()
                }, doOnError = {
                    Toast.makeText(this, it.exception?.message.orEmpty(), Toast.LENGTH_SHORT).show()
                })
        }
    }

    private fun bindMenu(menu: Menu?) {
        menu?.let {
            binding.ivImgMenuItemDetail.load(it.menuImg)
            binding.tvMenuName.text = it.menuName
            binding.tvMenuPrice.text = it.menuPrice.toCurrencyFormat()
            binding.tvMenuDesc.text = it.menuDesc
            binding.tvLocationDetail.text = getString(R.string.location)
            binding.btnAddToCart.text = getString(R.string.add_to_cart,it.menuPrice.toInt())
        }
    }

    private fun navigateToGoogleMaps() {
        val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:-6.301079121233476, 106.65341320967701"))
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    companion object {
        const val EXTRA_PRODUCT = "EXTRA_PRODUCT"
        fun startActivity(context: Context, menu: Menu) {
            val intent = Intent(context, DetailMenuActivity::class.java)
            intent.putExtra(EXTRA_PRODUCT, menu)
            context.startActivity(intent)
        }
    }

}