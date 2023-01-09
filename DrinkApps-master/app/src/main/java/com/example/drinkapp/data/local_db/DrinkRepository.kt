package com.example.drinkapp.data.local_db

import com.example.drinkapp.data.model.HistoryModel
import com.example.drinkapp.data.local_db.dao.HistoryDao
import com.example.drinkapp.data.model.CategoriesDBModel
import com.example.drinkapp.data.model.DrinkDBModel
import com.example.drinkapp.data.model.FavoritesModel
import kotlinx.coroutines.flow.Flow

class DrinkRepository(private val historyDao: HistoryDao) {

    val getDrinkHistory: Flow<List<HistoryModel>> = historyDao.getDrinkHistory()

    val getDrinkFavorites: Flow<List<FavoritesModel>> = historyDao.getFavorites()

    suspend fun insertHistory(historyModel: HistoryModel) {
        historyDao.insertHistory(historyModel)
    }

    suspend fun insertFavorite(historyModel: FavoritesModel) {
        historyDao.insertFavoriteDrink(historyModel)
    }

    suspend fun deleteHistory(id: Int) {
        historyDao.deleteHistory(id)
    }

    suspend fun deleteFavorite(id: Int) {
        historyDao.deleteFavorites(id)
    }

    suspend fun insertCategories(categories: List<CategoriesDBModel>) {
        historyDao.insertCategories(categories)
    }

    fun getCategories(): Flow<List<CategoriesDBModel>> = historyDao.getCategories()

    suspend fun updateCategory(status: Boolean, name: String) = historyDao.updateCategory(status,name)

    suspend  fun deleteCategories() {
        historyDao.deleteCategories()
    }

    suspend fun insertDrinks(drinks: List<DrinkDBModel>) {
        historyDao.insertDrinks(drinks)
    }

   suspend fun deleteDrinks() {
        historyDao.deleteDrinks()
    }

    fun getDrinks(category: String): Flow<List<DrinkDBModel>> = historyDao.getDrinks(category)

//    suspend fun

}