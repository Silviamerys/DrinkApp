package com.example.drinkapp.data.mappers

import com.example.drinkapp.data.model.CategoriesDBModel
import com.example.drinkapp.data.model.DrinkDBModel
import com.example.drinkapp.data.model.FavoritesModel
import com.example.drinkapp.data.model.HistoryModel
import com.example.drinkapp.domain.model.CategoriesNameModel
import com.example.drinkapp.domain.model.Drink
import com.example.drinkapp.domain.model.DrinkDetails
import com.example.drinkapp.domain.model.DrinkDetailsModel

class Mappers {

    companion object {
        fun mapDrinkToHistoryModel(drinkModel: Drink): HistoryModel {
            return HistoryModel(
                drinkModel.idDrink,
                drinkModel.strDrink,
                drinkModel.strDrinkThumb
            )
        }

        fun mapDrinkToFavoritesModel(drinkModel: Drink): FavoritesModel {
            return FavoritesModel(
                drinkModel.idDrink,
                drinkModel.strDrink,
                drinkModel.strDrinkThumb
            )
        }

        fun mapDrinkModelToDrinkDBModel(drinkModel: List<Drink>, category: String):List<DrinkDBModel> {
            return drinkModel.map {
                DrinkDBModel(
                    it.idDrink,
                    it.strDrink,
                    it.strDrinkThumb,
                    category
                )
            }
        }

        fun mapCategoriesDBModelToCategoriesNameModel(model : List<CategoriesDBModel>) : List<CategoriesNameModel>{
            return model.map {
                CategoriesNameModel(
                    it.strCategory
                )
            }
        }

    }

}