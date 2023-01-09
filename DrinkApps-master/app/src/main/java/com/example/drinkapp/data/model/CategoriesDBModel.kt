package com.example.drinkapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CategoryTable")
data class CategoriesDBModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "categoryName") val strCategory: String,
    @ColumnInfo(name = "categoryStatus") var status: Boolean = true
)