package com.example.drinkapp.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drinkapp.data.network_repository.ApiRepository
import com.example.drinkapp.domain.model.CategoriesModel
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val repo = ApiRepository()
    private val _ctgr = MutableLiveData<Response<CategoriesModel>>()
    val ctgr: LiveData<Response<CategoriesModel>> = _ctgr
    fun getCtgr() {
        viewModelScope.launch {
            setLoading(true)
            _ctgr.value = repo.getCategories()
            setLoading(false)
        }
    }

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    var isLoading: LiveData<Boolean> = _isLoading

    private fun setLoading(isLoading: Boolean) {
        _isLoading.postValue(isLoading)
    }


}