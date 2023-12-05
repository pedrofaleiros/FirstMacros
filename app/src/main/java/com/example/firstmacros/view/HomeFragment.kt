package com.example.firstmacros.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstmacros.databinding.FragmentHomeBinding
import com.example.firstmacros.service.AppConstants
import com.example.firstmacros.service.listener.ItemListener
import com.example.firstmacros.service.listener.MealListener
import com.example.firstmacros.service.model.ItemModel
import com.example.firstmacros.service.model.MealModel
import com.example.firstmacros.view.adapter.MealsAdapter
import com.example.firstmacros.viewmodel.LoginViewModel
import com.example.firstmacros.viewmodel.MealsViewModel
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private lateinit var mealsViewModel: MealsViewModel
    private lateinit var loginViewModel: LoginViewModel

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val adapter = MealsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mealsViewModel = ViewModelProvider(this)[MealsViewModel::class.java]
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

//        binding.recyclerMealsList.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = adapter
//        }

        binding.recyclerMealsList.layoutManager = LinearLayoutManager(context)
        binding.recyclerMealsList.adapter = adapter
        adapter.attachListener(getMealListener(), getItemListener())

        binding.floatingActionButton.setOnClickListener {
            loginViewModel.logoutUser()
        }

        mealsViewModel.getMeals()

        observe()

        return binding.root
    }

    private fun getMealListener(): MealListener = object : MealListener {
        override fun onClick(meal: MealModel) {
//            mealsViewModel.deleteMeal(id)
            Toast.makeText(context, "${meal.name}", Toast.LENGTH_SHORT).show()
        }

        override fun onLongClick(meal: MealModel) {
            val intent = Intent(context, MealActivity::class.java).apply {
                putExtra(AppConstants.EXTRAS.MEAL_NAME, meal.name)
                putExtra(AppConstants.EXTRAS.MEAL_ID, meal.id)
                putExtra(AppConstants.EXTRAS.MEAL_HOUR, meal.hour.toString())
                putExtra(AppConstants.EXTRAS.MEAL_MINUTES, meal.minutes.toString())
            }
            startActivity(intent)
        }
    }

    private fun getItemListener(): ItemListener = object : ItemListener {
        override fun onClick(item: ItemModel) {
            Toast.makeText(context, "${item.food.name} click", Toast.LENGTH_SHORT).show()
        }

        override fun onLongClick(item: ItemModel) {
            mealsViewModel.deleteItem(item.id)
            Snackbar.make(binding.root, "${item.food.name} deleted", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        mealsViewModel.meals.observe(viewLifecycleOwner) {
            adapter.getMeals(it)
        }

        mealsViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.recyclerMealsList.visibility = View.GONE
                binding.progressIndicator.visibility = View.VISIBLE
            } else {
                binding.recyclerMealsList.visibility = View.VISIBLE
                binding.progressIndicator.visibility = View.GONE
            }
        }

        loginViewModel.logout.observe(viewLifecycleOwner) {
            if (it) {
                mealsViewModel.clear()
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
            }
        }
    }
}