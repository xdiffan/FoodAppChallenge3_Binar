package com.challenge.foodappchallenge3.presentation.detailmenu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
import com.challenge.foodappchallenge3.R
import com.challenge.foodappchallenge3.databinding.FragmentDetailBinding
import com.challenge.foodappchallenge3.model.Menu




class FragmentDetail : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val menu: Menu? by lazy{
        FragmentDetailArgs.fromBundle(arguments as Bundle).menu
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProfileData()
        mapsClickListener()
        popBackStack()
        countingClickListener()
    }
    private fun popBackStack() {
        binding.ibArrowBack.setOnClickListener {
            findNavController().popBackStack()
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
            Toast.makeText(requireContext(), "Menu is null", Toast.LENGTH_SHORT).show()
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
    private var count: Int = 0
    private fun countingClickListener() {
        binding.icAdd.setOnClickListener{
            incrementCount()
        }
        binding.icMinus.setOnClickListener{
            decrementCount()
        }
    }

    private fun incrementCount() {
        count++
        binding.tvCounting.text = count.toString()
        val total = menu?.menuPrice?.toInt()!! * count
        binding.btnAddToCart.text = getString(R.string.add_to_cart,total)
    }

    private fun decrementCount() {
        count--
        if(count<=1){
            count=1
        }
        binding.tvCounting.text = count.toString()
        val total = menu?.menuPrice?.toInt()!! * count
        binding.btnAddToCart.text = getString(R.string.add_to_cart,total)
    }
}