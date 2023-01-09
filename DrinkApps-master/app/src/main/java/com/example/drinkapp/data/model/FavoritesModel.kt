package com.example.drinkapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorite")
data class FavoritesModel(
    @PrimaryKey val idDrink: String,
    @ColumnInfo(name = "drinkName") val drinkName: String,
    @ColumnInfo(name = "drinkImage") val drinkImage: String,
)