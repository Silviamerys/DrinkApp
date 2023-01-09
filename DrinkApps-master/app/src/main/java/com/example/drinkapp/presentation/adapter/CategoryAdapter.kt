package com.example.drinkapp.presentation.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

import com.example.drinkapp.domain.model.CategoriesModel
import com.example.drinkapp.domain.model.CategoriesNameModel
import com.example.drinkapp.presentation.ui.ARG_OBJECT
import com.example.drinkapp.presentation.ui.CategoryFragment

class CategoryAdapter(fragmentActivity: FragmentActivity, private val list : List<CategoriesNameModel>) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
       val fragment = CategoryFragment()
        fragment.arguments = Bundle().apply {
            putString(ARG_OBJECT,list[position].strCategory)
        }
    return fragment
    }

}