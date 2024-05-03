package com.planetsistemas.login.api

import com.google.gson.annotations.SerializedName

data class Result(
    val products: List<Product>,
    val total: Long,
    val skip: Long,
    val limit: Long,
)

data class Product(
    val id: Long,
    val title: String,
    val description: String,
    val price: Long,

    @SerializedName("discountPercentage") // real name on json
    val discount: Double,

    val rating: Double,
    val stock: Long,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>,
)
