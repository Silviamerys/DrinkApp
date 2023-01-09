package com.example.drinkapp.domain.model


import com.google.gson.annotations.SerializedName


data class CategoriesNameModel(
    @SerializedName("strCategory")
    val strCategory: String
)