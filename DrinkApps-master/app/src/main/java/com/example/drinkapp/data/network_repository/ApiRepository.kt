package com.example.drinkapp.data.network_repository

import com.example.drinkapp.domain.model.CategoriesModel
import com.example.drinkapp.domain.model.DrinkDetails
import com.example.drinkapp.domain.model.Drinks
import retrofit2.Response

class ApiRepository {

    suspend fun getCategories(): Response<CategoriesModel> {
        return RetrofitInstance.getCategories().getCategories()
    }

    suspend fun getDrinksByCategories(ctgrName: String): Response<Drinks> {
        return RetrofitInstance.getCategories().getDrinksByCategories(ctgrName)
    }

    suspend fun getDetailsDrinkById(id: String): Response<DrinkDetails> {
        return RetrofitInstance.getCategories().getDetailsDrinkById(id)
    }


    suspend fun getDrinksByName(name: String): Response<DrinkDetails> {
        return RetrofitInstance.getCategories().getDrinksByName(name)
    }

}