package com.challenge.foodappchallenge3.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Menu(
    val id: String = UUID.randomUUID().toString(),
    val menuName: String,
    val menuImg: String,
    val menuPrice: Double,
    val menuDesc: String,
) : Parcelable