package com.example.firstmacros.view.viewholder

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstmacros.databinding.MealListItemBinding
import com.example.firstmacros.service.listener.ItemListener
import com.example.firstmacros.service.listener.MealListener
import com.example.firstmacros.service.model.MealModel
import com.example.firstmacros.view.adapter.MealItemsAdapter

class MealsViewHolder(
    private val itemBinding: MealListItemBinding,
    val mealListener: MealListener,
    val itemListener: ItemListener,
) : RecyclerView.ViewHolder(itemBinding.root) {

    private lateinit var adapter: MealItemsAdapter

    fun bindData(meal: MealModel) {

        itemBinding.textMealName.text = meal.name
        if (meal.minutes > 10) {
            itemBinding.textMealHour.text = "${meal.hour}:${meal.minutes}"
        } else {
            itemBinding.textMealHour.text = "${meal.hour}:0${meal.minutes}"
        }

        adapter = MealItemsAdapter(meal.items)
        adapter.attachListener(itemListener)

        itemBinding.recyclerFoodsList.layoutManager = LinearLayoutManager(itemBinding.root.context)
        itemBinding.recyclerFoodsList.adapter = adapter

        itemBinding.textMealName.setOnClickListener {
            mealListener.onClick(meal)
        }

        itemBinding.imageMoreHoriz.setOnLongClickListener {
            mealListener.onLongClick(meal)
            true
        }
    }

}