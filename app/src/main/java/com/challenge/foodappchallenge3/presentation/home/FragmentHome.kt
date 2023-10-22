package com.challenge.foodappchallenge3.presentation.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.challenge.foodappchallenge3.R
import com.challenge.foodappchallenge3.data.local.datastore.UserPreferenceDataSourceImpl
import com.challenge.foodappchallenge3.data.local.datastore.appDataStore
import com.challenge.foodappchallenge3.data.network.api.datasource.RestaurantApiDataSource
import com.challenge.foodappchallenge3.data.network.api.service.RestaurantService
import com.challenge.foodappchallenge3.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.challenge.foodappchallenge3.data.repository.MenuRepository
import com.challenge.foodappchallenge3.data.repository.MenuRepositoryImpl
import com.challenge.foodappchallenge3.data.repository.UserRepositoryImpl
import com.challenge.foodappchallenge3.databinding.FragmentHomeBinding
import com.challenge.foodappchallenge3.model.Menu
import com.challenge.foodappchallenge3.presentation.detailmenu.DetailMenuActivity
import com.challenge.foodappchallenge3.presentation.main.MainViewModel
import com.challenge.foodappchallenge3.utils.GenericViewModelFactory
import com.challenge.foodappchallenge3.utils.PreferenceDataStoreHelperImpl
import com.challenge.foodappchallenge3.utils.proceedWhen
import com.google.firebase.auth.FirebaseAuth


class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding


    private val adapterMenu: MenuListAdapter by lazy {
        MenuListAdapter(AdapterLayoutMode.LINEAR) { menu: Menu ->
            navigateToDetail(menu)
        }
    }

    private val adapterCategory: CategoryListAdapter by lazy {
        CategoryListAdapter {
            homeViewModel.getMenus(it.categoryName.lowercase())
        }
    }

    private val viewModel: MainViewModel by viewModels {
        val dataStore = this.requireContext().appDataStore
        val dataStoreHelper = PreferenceDataStoreHelperImpl(dataStore)
        val userPreferenceDataSource = UserPreferenceDataSourceImpl(dataStoreHelper)
        GenericViewModelFactory.create(MainViewModel(userPreferenceDataSource))
    }

    private val homeViewModel: HomeViewModel by viewModels {
        val service = RestaurantService.invoke()
        val menuDataSource = RestaurantApiDataSource(service)
        val repo: MenuRepository = MenuRepositoryImpl(menuDataSource)
        val firebaseAuth = FirebaseAuth.getInstance()
        val dataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
        val userRepo = UserRepositoryImpl(dataSource)
        GenericViewModelFactory.create(HomeViewModel(repo, userRepo))
    }


    private fun getData() {
        homeViewModel.getCategories()
        homeViewModel.getMenus()
    }

    @SuppressLint("SetTextI18n")
    private fun showUserData() {
        val currentUser = homeViewModel.getCurrentUser()?.fullName
        binding.tvGrettings.text = "Hi $currentUser !"
    }

    private fun navigateToDetail(menu: Menu) {
        DetailMenuActivity.startActivity(requireContext(), menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewCategory()
        getData()
        setRecyclerViewMenu()
        setModeButton()
        showUserData()
    }

    private fun setObserveData() {
        homeViewModel.menus.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = { result ->
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.rvMenu.isVisible = true
                    result.payload?.let { menu ->
                        adapterMenu.setData(menu)
                    }
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.rvMenu.isVisible = false
                },
                doOnError = { err ->
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = err.exception?.message.orEmpty()
                    binding.rvMenu.isVisible = false
                }, doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = getString(R.string.error_empty)
                    binding.rvMenu.isVisible = false
                }
            )
        }
        homeViewModel.categories.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = { result ->
                    binding.rvCategory.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    result.payload?.let { category ->
                        adapterCategory.setData(category)
                    }
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.rvCategory.isVisible = false
                },
                doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = it.exception?.message.orEmpty()
                    binding.rvCategory.isVisible = false
                }
            )
        }
    }

    private fun setRecyclerViewMenu() {
        val span = if (adapterMenu.adapterLayoutMode == AdapterLayoutMode.LINEAR) 1 else 2
        binding.rvMenu.apply {
            layoutManager = GridLayoutManager(requireContext(), span)
            adapter = this@FragmentHome.adapterMenu
        }
        setObserveData()
    }

    private fun setModeButton() {
        viewModel.userLinearLayoutLiveData.observe(viewLifecycleOwner) {
            binding.switchListGrid.isChecked = it
        }

        binding.switchListGrid.setOnCheckedChangeListener { _, isUsingLinear ->
            viewModel.setLinearLayoutPref(isUsingLinear)
            (binding.rvMenu.layoutManager as GridLayoutManager).spanCount =
                if (isUsingLinear) 2 else 1
            adapterMenu.adapterLayoutMode =
                if (isUsingLinear) AdapterLayoutMode.GRID else AdapterLayoutMode.LINEAR
            setObserveData()
        }
    }

    private fun setRecyclerViewCategory() {
        binding.rvCategory.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = this@FragmentHome.adapterCategory
            setObserveData()
        }
    }
}