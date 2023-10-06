package com.challenge.foodappchallenge3.presentation.fragmenthome

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.challenge.foodappchallenge3.data.CategoryDataSource
import com.challenge.foodappchallenge3.data.CategoryDataSourceImplementation
import com.challenge.foodappchallenge3.data.MenuDataSource
import com.challenge.foodappchallenge3.data.MenuDataSourceImplementation
import com.challenge.foodappchallenge3.databinding.FragmentHomeBinding
import com.challenge.foodappchallenge3.model.Menu
import com.challenge.foodappchallenge3.presentation.detailmenu.DetailMenuActivity
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent


class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val menuDataSource : MenuDataSource by lazy { MenuDataSourceImplementation() }
    private val categoryDataSource:CategoryDataSource by lazy { CategoryDataSourceImplementation() }
    private val adapter: MenuListAdapter by lazy {
        MenuListAdapter(AdapterLayoutMode.LINEAR){
                menu: Menu -> navigateToDetail(menu)
        }
    }
    private val adapterCategory:CategoryListAdapter by lazy {
        CategoryListAdapter()
    }
    private fun navigateToDetail(menu: Menu? = null) {
        val action=Intent(requireContext(),DetailMenuActivity::class.java)
        action.putExtra("menu",menu)
        startActivity(action)

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
        adapter.setData(menuDataSource.getMenuData())
    }
    private fun setModeButton() {
        binding.switchListGrid.setOnCheckedChangeListener { _, isChecked ->
            (binding.rvMenu.layoutManager as GridLayoutManager).spanCount = if (isChecked) 2 else 1
            adapter.adapterLayoutMode = if(isChecked) AdapterLayoutMode.GRID else AdapterLayoutMode.LINEAR
            adapter.refreshList()
        }
    }
    private fun setRecyclerViewCategory() {
        binding.rvCategory.apply {
            layoutManager = FlexboxLayoutManager(requireContext())
            (layoutManager as FlexboxLayoutManager).flexDirection = FlexDirection.ROW
            (layoutManager as FlexboxLayoutManager).justifyContent = JustifyContent.SPACE_BETWEEN
            adapter = adapterCategory
            adapterCategory.setData(categoryDataSource.getCategoryData())
        }
    }
}