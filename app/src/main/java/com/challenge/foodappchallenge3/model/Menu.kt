package com.challenge.foodappchallenge3.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu(
    val id: Int? = null,
    val menuName: String,
    val menuImg: String,
    val menuPrice: Double,
    val menuDesc: String
) : Parcelable
