package com.example.firstmacros.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.firstmacros.service.listener.APIListener
import com.example.firstmacros.service.model.FoodModel
import com.example.firstmacros.service.repository.FoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodsViewModel(application: Application) : AndroidViewModel(application) {

    private val _foods = MutableLiveData<List<FoodModel>>()
    val foods: LiveData<List<FoodModel>> = _foods

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val repository = FoodRepository()

    fun getFoodsCoroutine() {
        _isLoading.value = true

        viewModelScope.launch {
            val foodsList = repository.getFoodsCoroutine()
            _foods.value = foodsList ?: emptyList()
            _isLoading.value = false
        }
    }

    fun getFoods() {
        _isLoading.value = true

        repository.getFoods(object : APIListener<List<FoodModel>> {
            override fun onSuccess(result: List<FoodModel>) {
                _foods.value = result
                _isLoading.value = false
            }

            override fun onFailure(message: String) {
                _isLoading.value = false
            }
        })
    }

    fun searchFoods(name: String) {
        if (name == "") {
            getFoods()
        } else {
            repository.searchFoods(name, object : APIListener<List<FoodModel>> {
                override fun onSuccess(result: List<FoodModel>) {
                    _foods.value = result
                }

                override fun onFailure(message: String) {
                }
            })
        }
    }
}