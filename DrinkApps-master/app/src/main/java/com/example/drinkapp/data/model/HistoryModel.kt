package com.example.drinkapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "History")
data class HistoryModel(
    @PrimaryKey val idDrink: String,
    @ColumnInfo(name = "drinkName") val drinkName: String,
    @ColumnInfo(name = "drinkImg") val drinkImg: String
)