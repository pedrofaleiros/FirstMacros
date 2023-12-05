package com.example.firstmacros.view

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstmacros.R
import com.example.firstmacros.databinding.ActivityMealBinding
import com.example.firstmacros.service.AppConstants
import com.example.firstmacros.service.listener.APIListener
import com.example.firstmacros.service.listener.FoodListener
import com.example.firstmacros.service.listener.ItemListener
import com.example.firstmacros.service.model.FoodModel
import com.example.firstmacros.service.model.ItemModel
import com.example.firstmacros.service.model.request.ItemRequest
import com.example.firstmacros.service.repository.ItemRepository
import com.example.firstmacros.view.adapter.FoodsAdapter
import com.example.firstmacros.viewmodel.FoodsViewModel
import com.google.android.material.snackbar.Snackbar

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding

    private val adapter = FoodsAdapter()
    private lateinit var foodsViewModel: FoodsViewModel
    private lateinit var addFoodViewModel: AddFoodViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        foodsViewModel = ViewModelProvider(this)[FoodsViewModel::class.java]
        addFoodViewModel = ViewModelProvider(this)[AddFoodViewModel::class.java]
        setContentView(binding.root)

        binding.recyclerFoodsList.layoutManager = LinearLayoutManager(this)
        binding.recyclerFoodsList.adapter = adapter
        adapter.attachListener(getFoodListener())

//        binding.searchIcon.setOnClickListener {
//            foodsViewModel.searchFoods(binding.editSearchFoods.text.toString())
//        }

        binding.buttonAddFood.setOnClickListener {
            handleClick()
        }

        foodsViewModel.getFoods()

        observe()

        getExtras()
    }

    fun getExtras() {
        val name = intent.getStringExtra(AppConstants.EXTRAS.MEAL_NAME)
        val hour = intent.getStringExtra(AppConstants.EXTRAS.MEAL_HOUR)?.toInt() ?: 8
        val minutes = intent.getStringExtra(AppConstants.EXTRAS.MEAL_MINUTES)?.toInt() ?: 0

        binding.textMealName.text = name
        if (minutes > 10) {
            binding.textMealHour.text = "$hour:$minutes"
        } else {
            binding.textMealHour.text = "$hour:0$minutes"
        }
    }

    private fun handleClick() {
        val id = intent.getStringExtra(AppConstants.EXTRAS.MEAL_ID) ?: ""
        val amount = binding.foodListItem.editFoodAmount.text.toString()
        addFoodViewModel.createItem(id, amount)
    }

    private fun observe() {
        foodsViewModel.foods.observe(this) {
            adapter.setFoods(it)
        }

        addFoodViewModel.selectedFood.observe(this) {
            if (it == null) {
                binding.foodListItem.editFoodAmount.visibility = View.GONE
                binding.foodListItem.textFoodName.text = ""
                binding.foodListItem.textFoodKcal.text = ""
                binding.foodListItem.textFoodCarb.text = ""
                binding.foodListItem.textFoodProt.text = ""
                binding.foodListItem.textFoodFats.text = ""
                binding.foodListItem.imageFoodEmpty.visibility = View.VISIBLE
            } else {
                binding.foodListItem.editFoodAmount.visibility = View.VISIBLE
                binding.foodListItem.editFoodAmount.setText("100")
                binding.foodListItem.textFoodName.text = it.name
                binding.foodListItem.textFoodKcal.text = it.kcal.toString()
                binding.foodListItem.textFoodCarb.text = it.carb.toString()
                binding.foodListItem.textFoodProt.text = it.prot.toString()
                binding.foodListItem.textFoodFats.text = it.fats.toString()
                binding.foodListItem.imageFoodEmpty.visibility = View.GONE
            }
        }

        addFoodViewModel.hasAddedFood.observe(this) {
            if (it) {
                Toast.makeText(applicationContext, "DEU certo", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "deu errado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getFoodListener(): FoodListener = object : FoodListener {
        override fun onClick(food: FoodModel) {
        }

        override fun onLongClick(food: FoodModel) {
            addFoodViewModel.setSelectedFood(food)
        }
    }
}

class AddFoodViewModel(application: Application) : AndroidViewModel(application) {

    private val itemRepository = ItemRepository()

    private val _selectedFood = MutableLiveData<FoodModel?>(null)
    val selectedFood: LiveData<FoodModel?> = _selectedFood

    private var foodAux: FoodModel? = null

    private val _hasAddedFood = MutableLiveData<Boolean>()
    val hasAddedFood: LiveData<Boolean> = _hasAddedFood

    fun setSelectedFood(food: FoodModel) {
        _selectedFood.value = food
        foodAux = food
    }

    fun createItem(mealId: String, amountStr: String) {

        var amount: Double
        try {
            amount = amountStr.toDouble()
        } catch (e: Exception) {
            amount = 0.0
        }

        if (foodAux != null && amount > 0) {
            val item = ItemRequest(foodAux!!.id, mealId, amount)
            itemRepository.createItem(item, object : APIListener<ItemModel> {
                override fun onSuccess(result: ItemModel) {
                    _hasAddedFood.value = true
                    _selectedFood.value = null
                    foodAux = null
                }

                override fun onFailure(message: String) {
                    _hasAddedFood.value = false
                }
            })
        } else {
            _hasAddedFood.value = false
        }
    }
}