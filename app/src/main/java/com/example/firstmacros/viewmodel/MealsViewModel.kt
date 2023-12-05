package com.example.firstmacros.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firstmacros.service.listener.APIListener
import com.example.firstmacros.service.model.ItemModel
import com.example.firstmacros.service.model.MealModel
import com.example.firstmacros.service.model.request.ItemRequest
import com.example.firstmacros.service.repository.ItemRepository
import com.example.firstmacros.service.repository.MealRepository

class MealsViewModel(application: Application) : AndroidViewModel(application) {

    private val mealRepository = MealRepository()
    private val itemRepository = ItemRepository()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _meals = MutableLiveData<List<MealModel>>()
    val meals: LiveData<List<MealModel>> = _meals

    fun updateMeals() {
        mealRepository.getUserMeals(object : APIListener<List<MealModel>> {
            override fun onSuccess(result: List<MealModel>) {
                _meals.value = result
            }

            override fun onFailure(message: String) {
                _meals.value = emptyList()
            }
        })
    }

    fun getMeals() {
        _isLoading.value = true

        mealRepository.getUserMeals(object : APIListener<List<MealModel>> {
            override fun onSuccess(result: List<MealModel>) {
                _meals.value = result
                _isLoading.value = false
            }

            override fun onFailure(message: String) {
                _meals.value = emptyList()
                _isLoading.value = false
            }
        })
    }

    fun deleteMeal(id: String) {
        mealRepository.deleteMeal(id, object : APIListener<MealModel> {
            override fun onSuccess(result: MealModel) {
                updateMeals()
            }

            override fun onFailure(message: String) {
            }
        })
    }

    fun deleteItem(id: String) {
        itemRepository.deleteItem(id, object : APIListener<ItemModel> {
            override fun onSuccess(result: ItemModel) {
                updateMeals()
            }

            override fun onFailure(message: String) {
                Log.d("pedro", "falhou")
            }
        })
    }

    fun clear() {
        _meals.value = emptyList()
    }

}