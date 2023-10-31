package com.challenge.foodappchallenge3.data.network.api.model.menu

import androidx.annotation.Keep
import com.challenge.foodappchallenge3.model.Menu
import com.google.gson.annotations.SerializedName

@Keep
data class MenuItemResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("alamat_resto")
    val restaurantAddress: String?,
    @SerializedName("detail")
    val description: String?,
    @SerializedName("harga")
    val price: Double?,
    @SerializedName("harga_format")
    val formattedPrice: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("nama")
    val name: String?
)

fun MenuItemResponse.toMenu() = Menu(
    id = this.id ?: 0,
    menuName = this.name.orEmpty(),
    menuPrice = this.price ?: 0.0,
    menuImg = this.imageUrl.orEmpty(),
    menuDesc = this.description.orEmpty()
)

fun Collection<MenuItemResponse>.toMenuList() = this.map { it.toMenu() }
