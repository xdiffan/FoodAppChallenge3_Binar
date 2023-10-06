package com.challenge.foodappchallenge3.presentation.detailmenu

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import coil.load
import com.challenge.foodappchallenge3.R
import com.challenge.foodappchallenge3.databinding.ActivityDetailMenuBinding
import com.challenge.foodappchallenge3.model.Menu

class DetailMenuActivity : AppCompatActivity() {
    private val binding:ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(
            layoutInflater)
    }
    private val menu:Menu?by lazy {
        intent.getParcelableExtra("menu")
    }
    override fun onCreate( savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showProfileData()
        mapsClickListener()
        popBackStack()
        countingClickListener()
        count=0
    }
    private fun popBackStack() {
        binding.ibArrowBack.setOnClickListener {
            super.onBackPressed()
        }
    }
    private fun showProfileData() {
        if(menu != null){
            binding.ivImgMenuItemDetail.load(menu!!.menuImg)
            binding.tvMenuName.text = menu?.menuName
            binding.tvMenuPrice.text = getString(R.string.rupiah,menu?.menuPrice?.toInt())
            binding.tvMenuDesc.text = menu?.menuDesc
            binding.tvLocationDetail.text = getString(R.string.location)
            binding.btnAddToCart.text = getString(R.string.add_to_cart, menu?.menuPrice?.toInt())
        } else{
            Toast.makeText(this, "Menu is null", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mapsClickListener() {
        binding.cvLocation.setOnClickListener {
            navigateToGoogleMaps()
        }
    }
    private fun navigateToGoogleMaps() {
        val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:-6.3016,$106.65337"))
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }
    private var count: Int? = null
    private fun countingClickListener() {
        binding.icAdd.setOnClickListener{
            incrementCount()
        }
        binding.icMinus.setOnClickListener{
            decrementCount()
        }
    }

    private fun incrementCount() {
        count=(count?:0)+1
        binding.tvCounting.text = count.toString()
        val total = (menu?.menuPrice?.toInt() ?: 0) * (count ?: 0)
        binding.btnAddToCart.text = getString(R.string.add_to_cart,total)
    }

    private fun decrementCount() {
        count = (count ?: 0) - 1
        if ((count ?: 0) <= 0) return
        binding.tvCounting.text = (count.toString())
        val total = (menu?.menuPrice?.toInt() ?: 0) * (count ?: 0)
        binding.btnAddToCart.text =
            getString(R.string.add_to_cart, total)
    }
}