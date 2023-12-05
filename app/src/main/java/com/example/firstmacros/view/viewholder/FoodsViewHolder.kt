package com.example.firstmacros.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.firstmacros.R
import com.example.firstmacros.databinding.FoodListItemBinding
import com.example.firstmacros.service.listener.FoodListener
import com.example.firstmacros.service.listener.ItemListener
import com.example.firstmacros.service.model.FoodModel
import com.example.firstmacros.service.model.ItemModel

class FoodsViewHolder(
    private val itemBinding: FoodListItemBinding,
    private val listener: FoodListener,
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bindData(food: FoodModel) {
        val context = itemBinding.root.context

        itemBinding.textFoodName.text = food.name
        itemBinding.textFoodAmount.text = "100 g"

        val kcal = food.kcal.toInt()
        itemBinding.textFoodKcal.text = context.getString(R.string.food_kcal, kcal.toString())
        itemBinding.textFoodCarb.text = food.carb.toString()
        itemBinding.textFoodProt.text = food.prot.toString()
        itemBinding.textFoodFats.text = food.fats.toString()

        itemBinding.listItem.setOnLongClickListener {
            listener.onLongClick(food)
            true
        }
    }
}