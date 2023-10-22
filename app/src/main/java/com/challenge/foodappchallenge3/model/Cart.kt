package com.challenge.foodappchallenge3.model



data class Cart(
    var id: Int? = null,
    var menuId: Int? = null,
    val menuName: String,
    val menuPrice: Double,
    val menuImgUrl: String,
    var itemQuantity: Int = 0,
    var itemNotes: String? = null,
)
