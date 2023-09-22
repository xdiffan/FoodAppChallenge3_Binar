package com.challenge.foodappchallenge3.presentation.fragmenthome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.challenge.foodappchallenge3.R
import com.challenge.foodappchallenge3.data.MenuDataSource
import com.challenge.foodappchallenge3.data.MenuDataSourceImpl
import com.challenge.foodappchallenge3.databinding.FragmentHomeBinding
import com.challenge.foodappchallenge3.model.Category
import com.challenge.foodappchallenge3.model.Menu
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent


class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val dataSource : MenuDataSource by lazy { MenuDataSourceImpl() }

    private val adapter: MenuListAdapter by lazy {
        MenuListAdapter(::navigateToDetail)
    }

    private fun navigateToDetail(menu: Menu) {
        TODO("Not yet implemented")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerViewCategory()
        setRecyclerViewMenu()
    }

    private fun setRecyclerViewMenu() {

        binding.rvMenu.adapter = adapter

        val layoutManagerMenu = FlexboxLayoutManager(requireContext())
        layoutManagerMenu.flexDirection = FlexDirection.ROW
        layoutManagerMenu.justifyContent = JustifyContent.SPACE_BETWEEN
        binding.rvMenu.layoutManager = layoutManagerMenu

        adapter.setData(dataSource.getMenuData())

    }

    private fun setRecyclerViewCategory() {

        // Add Category List
        val categoryList = mutableListOf(
            Category("Nasi", R.drawable.iv_fried_rice),
            Category("Mie", R.drawable.iv_mie),
            Category("Snack", R.drawable.iv_snack),
            Category("Minuman", R.drawable.iv_drink),

        )

        // Create Adapter
        val recyclerViewAdapterCategory = CategoryListAdapter(categoryList)

        // Create Layout Manager
        val layoutManagerCategory = FlexboxLayoutManager(requireContext())
        layoutManagerCategory.flexDirection = FlexDirection.ROW
        layoutManagerCategory.justifyContent = JustifyContent.SPACE_BETWEEN

        // Create RecyclerView
        val recyclerViewCategory = binding.rvCategory

        // Set LayoutManager on RecyclerView
        recyclerViewCategory.layoutManager = layoutManagerCategory

        // Set Adapter on RecyclerView
        recyclerViewCategory.adapter = recyclerViewAdapterCategory
    }
}