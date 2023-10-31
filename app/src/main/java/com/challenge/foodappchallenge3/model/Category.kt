package com.challenge.foodappchallenge3.model

import java.util.UUID

data class Category(
    val id: String = UUID.randomUUID().toString(),
    val categoryName: String,
    val categoryImgSrc: String
)
