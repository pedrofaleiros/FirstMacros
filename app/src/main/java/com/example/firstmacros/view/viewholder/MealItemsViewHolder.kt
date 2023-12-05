package com.example.firstmacros.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.firstmacros.databinding.FoodListItemBinding
import com.example.firstmacros.service.listener.ItemListener
import com.example.firstmacros.service.model.ItemModel

class MealItemsViewHolder(
    private val itemBinding: FoodListItemBinding,
    val itemListener: ItemListener,
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bindData(item: ItemModel) {

        itemBinding.textFoodName.text = item.food.name
        itemBinding.textFoodAmount.text = "${item.amount} g"

        var kcal: Double = 0.0
        var carb: Double = 0.0
        var prot: Double = 0.0
        var fats: Double = 0.0

        kcal = item.food.kcal / 100 * item.amount
        carb = item.food.carb / 100 * item.amount
        prot = item.food.prot / 100 * item.amount
        fats = item.food.fats / 100 * item.amount

        itemBinding.textFoodKcal.text = "${String.format("%.1f", kcal)} Kcal"
        itemBinding.textFoodCarb.text = String.format("%.1f", carb)
        itemBinding.textFoodProt.text = String.format("%.1f", prot)
        itemBinding.textFoodFats.text = String.format("%.1f", fats)

        itemBinding.listItem.setOnLongClickListener {
            itemListener.onLongClick(item)
            true
        }

        itemBinding.listItem.setOnClickListener {
            itemListener.onClick(item)
        }
    }
}