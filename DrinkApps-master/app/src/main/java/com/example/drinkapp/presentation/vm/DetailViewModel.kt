package com.example.drinkapp.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drinkapp.data.network_repository.ApiRepository
import com.example.drinkapp.domain.model.DrinkDetails
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailViewModel:ViewModel() {


    private val repo = ApiRepository()
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    var isLoading: LiveData<Boolean> = _isLoading

    private fun setLoading(isLoading: Boolean) {
        _isLoading.postValue(isLoading)
    }

    fun getDetailsDrinkById(id: String): LiveData<Response<DrinkDetails>> {
        val detail = MutableLiveData<Response<DrinkDetails>>()
        viewModelScope.launch {
            setLoading(true)
            detail.value = repo.getDetailsDrinkById(id)
            setLoading(false)
        }
        return detail
    }
}