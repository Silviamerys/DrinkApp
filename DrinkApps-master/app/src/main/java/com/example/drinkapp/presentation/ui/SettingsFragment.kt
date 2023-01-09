package com.example.drinkapp.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.drinkapp.App
import com.example.drinkapp.R
import com.example.drinkapp.data.model.CategoriesDBModel
import com.example.drinkapp.presentation.vm.RoomViewModel
import com.example.drinkapp.presentation.vm.RoomViewModelFactory

class SettingsFragment : Fragment(), View.OnClickListener {

    private lateinit var cbOrdinary: CheckBox
    private lateinit var cbCocktail: CheckBox
    private lateinit var cbShake: CheckBox
    private lateinit var cbOther: CheckBox
    private lateinit var cbCocoa: CheckBox
    private lateinit var cbShot: CheckBox
    private lateinit var cbCoffee: CheckBox
    private lateinit var cbHomemade: CheckBox
    private lateinit var cbPunch: CheckBox
    private lateinit var cbBeer: CheckBox
    private lateinit var cbSoft: CheckBox

    private lateinit var array: List<CategoriesDBModel>

    private val roomViewModel: RoomViewModel by viewModels {
        RoomViewModelFactory((activity?.application as App).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        cbOrdinary = view.findViewById(R.id.cb_ordinary)
        cbOrdinary.setOnClickListener(this)

        cbCocktail = view.findViewById(R.id.cb_cocktail)
        cbCocktail.setOnClickListener(this)

        cbShake = view.findViewById(R.id.cb_shake)
        cbShake.setOnClickListener(this)

        cbOther = view.findViewById(R.id.cb_other)
        cbOther.setOnClickListener(this)

        cbCocoa = view.findViewById(R.id.cb_cocoa)
        cbCocoa.setOnClickListener(this)

        cbShot = view.findViewById(R.id.cb_shot)
        cbShot.setOnClickListener(this)

        cbCoffee = view.findViewById(R.id.cb_coffee)
        cbCoffee.setOnClickListener(this)

        cbHomemade = view.findViewById(R.id.cb_homemade)
        cbHomemade.setOnClickListener(this)

        cbPunch = view.findViewById(R.id.cb_punch)
        cbPunch.setOnClickListener(this)

        cbBeer = view.findViewById(R.id.cb_beer)
        cbBeer.setOnClickListener(this)

        cbSoft = view.findViewById(R.id.cb_soft)
        cbSoft.setOnClickListener(this)

        roomViewModel.getCategories.observe(viewLifecycleOwner) {
            array = it
            cbOrdinary.isChecked = it[it.indexOfFirst { it.strCategory == "Ordinary Drink" }].status
            cbCocktail.isChecked = it[it.indexOfFirst { it.strCategory == "Cocktail" }].status
            cbShake.isChecked = it[it.indexOfFirst { it.strCategory == "Shake" }].status
            val index = it.indexOfFirst { it.strCategory == "Other/Unknown" }
            if (index >=0)
                cbOther.isChecked = it[index].status
            cbCocoa.isChecked = it[it.indexOfFirst { it.strCategory == "Cocoa" }].status
            cbShot.isChecked = it[it.indexOfFirst { it.strCategory == "Shot" }].status
            cbCoffee.isChecked = it[it.indexOfFirst { it.strCategory == "Coffee / Tea" }].status
            cbHomemade.isChecked =
                it[it.indexOfFirst { it.strCategory == "Homemade Liqueur" }].status
            cbPunch.isChecked =
                it[it.indexOfFirst { it.strCategory == "Punch / Party Drink" }].status
            cbBeer.isChecked = it[it.indexOfFirst { it.strCategory == "Beer" }].status
            cbSoft.isChecked = it[it.indexOfFirst { it.strCategory == "Soft Drink" }].status

        }

        return view
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.cb_ordinary -> {
                changeStatus("Ordinary Drink", cbOrdinary)
            }
            R.id.cb_cocktail -> {
                changeStatus("Cocktail", cbCocktail)
            }
            R.id.cb_shake -> {
                changeStatus("Shake", cbShake)
            }
            R.id.cb_other -> {
                changeStatus("Other/Unknown", cbOther)
            }
            R.id.cb_cocoa -> {
                changeStatus("Cocoa", cbCocoa)
            }
            R.id.cb_shot -> {
                changeStatus("Shot", cbShot)
            }
            R.id.cb_coffee -> {
                changeStatus("Coffee / Tea", cbCoffee)
            }
            R.id.cb_punch -> {
                changeStatus("Punch / Party Drink", cbPunch)
            }
            R.id.cb_beer -> {
                changeStatus("Beer", cbBeer)
            }
            R.id.cb_soft -> {
                changeStatus("Soft Drink", cbSoft)
            }
            R.id.cb_homemade -> {
                changeStatus("Homemade Liqueur", cbHomemade)
            }
        }

    }

    private fun changeStatus(categoryName: String, checkBox: CheckBox) {
        if (array[array.indexOfFirst { it.strCategory == categoryName }].status) {
            roomViewModel.updateCategory(false, categoryName)
            checkBox.isChecked = false
        } else {
            roomViewModel.updateCategory(true, categoryName)
            checkBox.isChecked = true
        }
    }

}