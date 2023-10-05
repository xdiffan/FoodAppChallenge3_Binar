package com.challenge.foodappchallenge3.presentation.fragmenthome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.challenge.foodappchallenge3.data.MenuDataSource
import com.challenge.foodappchallenge3.data.MenuDataSourceImplementation
import com.challenge.foodappchallenge3.databinding.FragmentHomeBinding
import com.challenge.foodappchallenge3.model.Category
import com.challenge.foodappchallenge3.model.Menu
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent


class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val dataSource : MenuDataSource by lazy { MenuDataSourceImplementation() }

    private val adapter: MenuListAdapter by lazy {
        MenuListAdapter(AdapterLayoutMode.LINEAR){
                menu: Menu -> navigateToDetail(menu)
        }
    }
    private fun navigateToDetail(menu: Menu? = null) {
        val action = FragmentHomeDirections.actionFragmentHomeToFragmentDetail(menu)
        findNavController().navigate(action)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerViewCategory()
        setRecyclerViewMenu()
        setModeButton()
    }
    private fun setRecyclerViewMenu() {
        val span = if(adapter.adapterLayoutMode == AdapterLayoutMode.LINEAR) 1 else 2
        binding.rvMenu.apply {
            layoutManager = GridLayoutManager(requireContext(),span)
            adapter = this@FragmentHome.adapter
        }
        adapter.setData(dataSource.getMenuData())
    }
    private fun setModeButton() {
        binding.switchListGrid.setOnCheckedChangeListener { _, isChecked ->
            (binding.rvMenu.layoutManager as GridLayoutManager).spanCount = if (isChecked) 2 else 1
            adapter.adapterLayoutMode = if(isChecked) AdapterLayoutMode.GRID else AdapterLayoutMode.LINEAR
            adapter.refreshList()
        }
    }
    private fun setRecyclerViewCategory() {
        val categoryList = mutableListOf(
            Category("Nasi", "https://raw.githubusercontent.com/xdiffan/Asset-Challenge-FoodApp/main/iv_rice.webp"),
            Category("Mie", "https://raw.githubusercontent.com/xdiffan/Asset-Challenge-FoodApp/main/iv_mie.webp"),
            Category("Snack", "https://raw.githubusercontent.com/xdiffan/Asset-Challenge-FoodApp/main/iv_snack.webp"),
            Category("Minuman", "https://raw.githubusercontent.com/xdiffan/Asset-Challenge-FoodApp/main/iv_drink.webp")

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