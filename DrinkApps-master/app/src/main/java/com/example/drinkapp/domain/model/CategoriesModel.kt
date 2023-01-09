package com.example.drinkapp.domain.model


import com.google.gson.annotations.SerializedName



data class CategoriesModel(
    @SerializedName("drinks")
    val categoriesNameModels: List<CategoriesNameModel>
)