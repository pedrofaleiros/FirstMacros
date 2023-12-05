package com.example.firstmacros.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firstmacros.databinding.MealListItemBinding
import com.example.firstmacros.service.listener.ItemListener
import com.example.firstmacros.service.listener.MealListener
import com.example.firstmacros.service.model.MealModel
import com.example.firstmacros.view.viewholder.MealsViewHolder

class MealsAdapter : RecyclerView.Adapter<MealsViewHolder>() {

    private var mealsList: List<MealModel> = arrayListOf()
    private lateinit var mealListener: MealListener
    private lateinit var itemListener: ItemListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = MealListItemBinding.inflate(inflater, parent, false)

        return MealsViewHolder(itemBinding, mealListener, itemListener)
    }

    override fun getItemCount(): Int = mealsList.count()

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
        holder.bindData(mealsList[position])
    }

    fun getMeals(meals: List<MealModel>) {
        mealsList = meals
        notifyDataSetChanged()
    }

    fun attachListener(listenerMeal: MealListener, listenerItem: ItemListener) {
        mealListener = listenerMeal
        itemListener = listenerItem
    }
}