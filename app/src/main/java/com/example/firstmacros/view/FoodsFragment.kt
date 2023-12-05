package com.example.firstmacros.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstmacros.R
import com.example.firstmacros.databinding.FoodListItemBinding
import com.example.firstmacros.databinding.FragmentFoodsBinding
import com.example.firstmacros.service.listener.FoodListener
import com.example.firstmacros.service.model.FoodModel
import com.example.firstmacros.view.adapter.FoodsAdapter
import com.example.firstmacros.viewmodel.FoodsViewModel

class FoodsFragment : Fragment() {

    private lateinit var binding: FragmentFoodsBinding

    private lateinit var viewModel: FoodsViewModel
    private val adapter = FoodsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFoodsBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[FoodsViewModel::class.java]

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.recyclerFoodsList.layoutManager = LinearLayoutManager(context)
        binding.recyclerFoodsList.adapter = adapter

        adapter.attachListener(getFoodListener())

        observe()

//        viewModel.getFoods()
        viewModel.getFoodsCoroutine()

        return binding.root
    }

    private fun getFoodListener(): FoodListener = object : FoodListener {
        override fun onClick(food: FoodModel) {
        }

        override fun onLongClick(food: FoodModel) {
        }
    }

    private fun observe() {
        viewModel.foods.observe(viewLifecycleOwner) {
            adapter.setFoods(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.recyclerFoodsList.visibility = View.GONE
                binding.progressIndicator.visibility = View.VISIBLE
            } else {
                binding.recyclerFoodsList.visibility = View.VISIBLE
                binding.progressIndicator.visibility = View.GONE
            }
        }
    }
}



