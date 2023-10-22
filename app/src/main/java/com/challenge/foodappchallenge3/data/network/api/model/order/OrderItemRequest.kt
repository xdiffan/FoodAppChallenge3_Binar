package com.challenge.foodappchallenge3.data.network.api.model.order

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class OrderItemRequest (
    @SerializedName("nama")
    val name: String?,
    @SerializedName("qty")
    val qty: Int?,
    @SerializedName("catatan")
    val note: String?,
    @SerializedName("harga")
    val price: Double?
)
