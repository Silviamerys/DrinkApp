package com.example.drinkapp.presentation.vm

import android.util.Log
import androidx.lifecycle.*
import com.example.drinkapp.data.local_db.DrinkRepository
import com.example.drinkapp.data.model.CategoriesDBModel
import com.example.drinkapp.data.model.DrinkDBModel
import com.example.drinkapp.data.model.FavoritesModel
import com.example.drinkapp.data.model.HistoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class RoomViewModel(private val repository: DrinkRepository) : ViewModel() {

    val allHistory: LiveData<List<HistoryModel>> = repository.getDrinkHistory.asLiveData()

    val allFavorites: LiveData<List<FavoritesModel>> = repository.getDrinkFavorites.asLiveData()

    fun insertHistory(historyModel: HistoryModel) {
        viewModelScope.launch {
            repository.insertHistory(historyModel)
        }
    }

    fun insertFavorites(favorites: FavoritesModel) {
        viewModelScope.launch {
            repository.insertFavorite(favorites)
        }
    }

    fun deleteHistory(id: Int) {
        viewModelScope.launch { repository.deleteHistory(id) }
    }

    fun deleteFavorites(id: Int) {
        viewModelScope.launch { repository.deleteFavorite(id) }
    }

    fun insertCategories(categories: List<CategoriesDBModel>) {
        viewModelScope.launch {
            repository.insertCategories(categories)
        }
    }

    val getCategories: LiveData<List<CategoriesDBModel>> = repository.getCategories().asLiveData()

    fun updateCategory(status: Boolean, name: String) {
        viewModelScope.launch {
            repository.updateCategory(status, name)
        }
    }

    fun deleteCategories() {
        viewModelScope.launch {
            repository.deleteCategories()
        }

    }

    fun insertDrinks(drinks: List<DrinkDBModel>) {
        viewModelScope.launch {
            repository.insertDrinks(drinks)
        }
    }

    fun getDrinks(category: String): LiveData<List<DrinkDBModel>> =
        repository.getDrinks(category).asLiveData()

    fun deleteDrinks() {
        viewModelScope.launch {
            repository.deleteDrinks()
        }
    }

}

class RoomViewModelFactory(private val repository: DrinkRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoomViewModel::class.java)) {
            return RoomViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}