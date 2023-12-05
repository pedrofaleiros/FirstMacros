package com.example.firstmacros.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firstmacros.databinding.FoodListItemBinding
import com.example.firstmacros.service.listener.FoodListener
import com.example.firstmacros.service.listener.ItemListener
import com.example.firstmacros.service.model.FoodModel
import com.example.firstmacros.view.viewholder.FoodsViewHolder

class FoodsAdapter : RecyclerView.Adapter<FoodsViewHolder>() {

    private var foods: List<FoodModel> = arrayListOf()

    private lateinit var listener: FoodListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = FoodListItemBinding.inflate(inflater, parent, false)

        return FoodsViewHolder(itemBinding, listener)
    }

    override fun getItemCount(): Int = foods.count()

    override fun onBindViewHolder(holder: FoodsViewHolder, position: Int) {
        holder.bindData(foods[position])
    }

    fun setFoods(foodList: List<FoodModel>) {
        foods = foodList
        notifyDataSetChanged()
    }

    fun attachListener(newListener: FoodListener) {
        listener = newListener
    }
}