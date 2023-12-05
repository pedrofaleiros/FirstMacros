package com.example.firstmacros.view.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firstmacros.databinding.FoodListItemBinding
import com.example.firstmacros.databinding.MealListItemBinding
import com.example.firstmacros.service.listener.ItemListener
import com.example.firstmacros.service.model.ItemModel
import com.example.firstmacros.service.model.MealModel
import com.example.firstmacros.view.viewholder.MealItemsViewHolder

class MealItemsAdapter(private var itemsList: List<ItemModel>) : RecyclerView.Adapter<MealItemsViewHolder>() {

    private lateinit var itemListener: ItemListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealItemsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = FoodListItemBinding.inflate(inflater, parent, false)

        return MealItemsViewHolder(itemBinding, itemListener)
    }

    override fun getItemCount(): Int = itemsList.count()

    override fun onBindViewHolder(holder: MealItemsViewHolder, position: Int) {
        holder.bindData(itemsList[position])
    }

    fun attachListener(listener: ItemListener) {
        itemListener = listener
    }
}