package com.example.drinkapp.data.local_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.drinkapp.data.model.HistoryModel
import com.example.drinkapp.data.local_db.dao.HistoryDao
import com.example.drinkapp.data.model.CategoriesDBModel
import com.example.drinkapp.data.model.DrinkDBModel
import com.example.drinkapp.data.model.FavoritesModel

@Database(
    entities = [HistoryModel::class, FavoritesModel::class, CategoriesDBModel::class, DrinkDBModel::class],
    version = 6,
    exportSchema = false
)
abstract class DrinkRoomDataBase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    companion object {
        private var Instance: DrinkRoomDataBase? = null

        fun getDataBase(context: Context): DrinkRoomDataBase {
            return Instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DrinkRoomDataBase::class.java,
                    "drink_database"
                )
                    .build()
                Instance = instance
                instance
            }
        }
    }

}