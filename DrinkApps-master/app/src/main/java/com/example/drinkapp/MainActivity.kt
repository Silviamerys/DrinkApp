package com.example.drinkapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.drinkapp.presentation.ui.*

import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val bottomNavigationView: BottomNavigationView = findViewById(R.id.btn_view)
        setCurrentFragment(MainFragment())

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.category -> setCurrentFragment(MainFragment())
                R.id.history -> setCurrentFragment(HistoryFragment())
                R.id.settings -> setCurrentFragment(SettingsFragment())
                R.id.favorite -> setCurrentFragment(FavoriteFragment())
                R.id.search -> setCurrentFragment(SearchFragment())
            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment, fragment)
            commit()
        }


}