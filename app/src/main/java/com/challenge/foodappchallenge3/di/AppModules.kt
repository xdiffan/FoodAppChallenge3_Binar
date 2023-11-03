package com.challenge.foodappchallenge3.di

import com.challenge.foodappchallenge3.data.local.database.AppDatabase
import com.challenge.foodappchallenge3.data.local.database.datasource.CartDataSource
import com.challenge.foodappchallenge3.data.local.database.datasource.CartDatabaseDataSource
import com.challenge.foodappchallenge3.data.local.datastore.UserPreferenceDataSource
import com.challenge.foodappchallenge3.data.local.datastore.UserPreferenceDataSourceImpl
import com.challenge.foodappchallenge3.data.local.datastore.appDataStore
import com.challenge.foodappchallenge3.data.network.api.datasource.RestaurantApiDataSource
import com.challenge.foodappchallenge3.data.network.api.datasource.RestaurantDataSource
import com.challenge.foodappchallenge3.data.network.api.service.RestaurantService
import com.challenge.foodappchallenge3.data.network.firebase.auth.FirebaseAuthDataSource
import com.challenge.foodappchallenge3.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.challenge.foodappchallenge3.data.repository.CartRepository
import com.challenge.foodappchallenge3.data.repository.CartRepositoryImpl
import com.challenge.foodappchallenge3.data.repository.MenuRepository
import com.challenge.foodappchallenge3.data.repository.MenuRepositoryImpl
import com.challenge.foodappchallenge3.data.repository.UserRepository
import com.challenge.foodappchallenge3.data.repository.UserRepositoryImpl
import com.challenge.foodappchallenge3.presentation.cart.CartViewModel
import com.challenge.foodappchallenge3.presentation.checkout.CheckoutViewModel
import com.challenge.foodappchallenge3.presentation.detailmenu.DetailMenuViewModel
import com.challenge.foodappchallenge3.presentation.home.HomeViewModel
import com.challenge.foodappchallenge3.presentation.login.LoginViewModel
import com.challenge.foodappchallenge3.presentation.profile.ProfileViewModel
import com.challenge.foodappchallenge3.presentation.register.RegisterViewModel
import com.challenge.foodappchallenge3.presentation.splashscreen.SplashViewModel
import com.challenge.foodappchallenge3.utils.PreferenceDataStoreHelper
import com.challenge.foodappchallenge3.utils.PreferenceDataStoreHelperImpl
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {
    private val localModule = module {
        single { AppDatabase.getInstance(androidContext()) }
        single { get<AppDatabase>().cartDao() }
        single { androidContext().appDataStore }
        single<PreferenceDataStoreHelper> { PreferenceDataStoreHelperImpl(get()) }
    }
    private val networkModule = module {
        single { RestaurantService.invoke() }
        single { FirebaseAuth.getInstance() }
    }
    private val dataSourceModule = module {
        single<CartDataSource> { CartDatabaseDataSource(get()) }
        single<UserPreferenceDataSource> { UserPreferenceDataSourceImpl(get()) }
        single<RestaurantDataSource> { RestaurantApiDataSource(get()) }
        single<FirebaseAuthDataSource> { FirebaseAuthDataSourceImpl(get()) }
    }
    private val repositoryModule = module {
        single<CartRepository> { CartRepositoryImpl(get(), get()) }
        single<UserRepository> { UserRepositoryImpl(get()) }
        single<MenuRepository> { MenuRepositoryImpl(get()) }
    }
    private val viewModelModule = module {
        viewModelOf(::HomeViewModel)
        viewModelOf(::SplashViewModel)
        viewModelOf(::LoginViewModel)
        viewModelOf(::RegisterViewModel)
        viewModelOf(::ProfileViewModel)
        viewModelOf(::CartViewModel)
        viewModelOf(::CheckoutViewModel)
        viewModelOf(::DetailMenuViewModel)
    }

    val modules: List<Module> = listOf(
        localModule,
        networkModule,
        dataSourceModule,
        repositoryModule,
        viewModelModule
    )
}
