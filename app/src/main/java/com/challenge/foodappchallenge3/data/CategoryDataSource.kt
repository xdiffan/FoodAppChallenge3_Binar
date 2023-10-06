package com.challenge.foodappchallenge3.data

import com.challenge.foodappchallenge3.model.Category

interface CategoryDataSource {
    fun getCategoryData() : List<Category>
}

class CategoryDataSourceImplementation() : CategoryDataSource {
    override fun getCategoryData(): List<Category> =
        listOf(
            Category(categoryName = "Nasi", categoryImgSrc = "https://raw.githubusercontent.com/xdiffan/Asset-Challenge-FoodApp/main/iv_rice.webp"),
            Category(categoryName = "Mie", categoryImgSrc = "https://raw.githubusercontent.com/xdiffan/Asset-Challenge-FoodApp/main/iv_mie.webp"),
            Category(categoryName = "Snack", categoryImgSrc = "https://raw.githubusercontent.com/xdiffan/Asset-Challenge-FoodApp/main/iv_snack.webp"),
            Category(categoryName = "Minuman", categoryImgSrc = "https://raw.githubusercontent.com/xdiffan/Asset-Challenge-FoodApp/main/iv_drink.webp")
        )
}

