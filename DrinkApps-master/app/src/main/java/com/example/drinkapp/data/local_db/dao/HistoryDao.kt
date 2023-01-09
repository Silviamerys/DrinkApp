package com.example.drinkapp.data.local_db.dao

import androidx.room.*
import com.example.drinkapp.data.model.CategoriesDBModel
import com.example.drinkapp.data.model.DrinkDBModel
import com.example.drinkapp.data.model.FavoritesModel
import com.example.drinkapp.data.model.HistoryModel
import com.example.drinkapp.domain.model.CategoriesNameModel
import kotlinx.coroutines.flow.Flow


@Dao
interface HistoryDao {

    //History
    @Query("Select * from History")
    fun getDrinkHistory(): Flow<List<HistoryModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHistory(history: HistoryModel)

    @Query("Delete from History")
    suspend fun deleteAllHistory()

    @Query("Delete from History Where idDrink = :id")
    suspend fun deleteHistory(id: Int)

    //Favorites
    @Query("Select * from Favorite")
    fun getFavorites(): Flow<List<FavoritesModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteDrink(favoritesModel: FavoritesModel)

    @Query("Delete from Favorite where idDrink = :id")
    suspend fun deleteFavorites(id: Int)

//    @Query("SELECT EXISTS (SELECT 1 FROM example_table WHERE id = :id)")
//    fun exists(id: Int): Boolean

    //Category
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategories(categories: List<CategoriesDBModel>)


    @Query("Select * from CategoryTable ")
    fun getCategories(): Flow<List<CategoriesDBModel>>

    @Query("UPDATE CategoryTable SET categoryStatus = :status WHERE categoryName = :name")
    suspend fun updateCategory(status: Boolean, name: String)

    @Query("Delete from CategoryTable")
    suspend fun deleteCategories()


    //
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDrinks(drinks: List<DrinkDBModel>)

    @Query("Select * from DrinkTable where drinkCategory = :category")
    fun getDrinks(category: String): Flow<List<DrinkDBModel>>

    @Query("Delete from DrinkTable")
    suspend fun deleteDrinks()


}