package com.example.drinkapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DrinkTable")
data class DrinkDBModel(
    @PrimaryKey val idDrink: String,
    @ColumnInfo(name = "drinkName") val stringDrink: String,
    @ColumnInfo(name = "drinkImage") val strDrinkThumb: String,
    @ColumnInfo(name = "drinkCategory") val strDrinkCategory: String
)