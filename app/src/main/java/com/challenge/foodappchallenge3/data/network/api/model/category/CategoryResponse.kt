package com.challenge.foodappchallenge3.data.network.api.model.category

import androidx.annotation.Keep
import com.challenge.foodappchallenge3.model.Category
import com.google.gson.annotations.SerializedName

@Keep
data class CategoryResponse(
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("nama")
    val name: String?
)

fun CategoryResponse.toCategory() = Category(
    categoryName = this.name.orEmpty(),
    categoryImgSrc = this.imageUrl.orEmpty()
)

fun Collection<CategoryResponse>.toCategoryList() = this.map { it.toCategory() }
