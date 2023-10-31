package com.challenge.foodappchallenge3.presentation.detailmenu

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.challenge.foodappchallenge3.R
import com.challenge.foodappchallenge3.databinding.ActivityDetailMenuBinding
import com.challenge.foodappchallenge3.model.Menu
import com.challenge.foodappchallenge3.utils.proceedWhen
import com.challenge.foodappchallenge3.utils.toCurrencyFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailMenuActivity : AppCompatActivity() {
    private val binding: ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(
            layoutInflater
        )
    }
    private val viewModel: DetailMenuViewModel by viewModel { parametersOf(intent.extras) }

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
        binding.btnAddToCart.setOnClickListener {
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
                },
                doOnError = {
                    Toast.makeText(this, it.exception?.message.orEmpty(), Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun bindMenu(menu: Menu?) {
        menu?.let {
            binding.ivImgMenuItemDetail.load(it.menuImg)
            binding.tvMenuName.text = it.menuName
            binding.tvMenuPrice.text = it.menuPrice.toCurrencyFormat()
            binding.tvMenuDesc.text = it.menuDesc
            binding.tvLocationDetail.text = getString(R.string.location)
            binding.btnAddToCart.text = getString(R.string.add_to_cart)
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
