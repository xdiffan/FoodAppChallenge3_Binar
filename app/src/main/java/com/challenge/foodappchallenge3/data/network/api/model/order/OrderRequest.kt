package com.challenge.foodappchallenge3.data.network.api.model.order

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class OrderRequest(
    @SerializedName("username")
    val username: String?,

    @SerializedName("total")
    val total: Double?,

    @SerializedName("orders")
    val orders: List<OrderItemRequest>?
)
