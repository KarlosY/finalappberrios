package com.example.practica_ec3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate

import androidx.fragment.app.Fragment
import com.example.practica_ec3.fragments.FavoriteFragment
import com.example.practica_ec3.fragments.FirebaseFragment
import com.example.practica_ec3.fragments.HomeFragment
import com.example.practica_ec3.fragments.InfoFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var addDogsButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configurar el modo actual (Light, Dark, Sistema)
        val currentMode = AppCompatDelegate.getDefaultNightMode()
        AppCompatDelegate.setDefaultNightMode(currentMode)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        addDogsButton = findViewById(R.id.add_dogs) // Add this line

        addDogsButton.setOnClickListener {
            val intent = Intent(this, AddDogActivity::class.java)
            startActivity(intent)
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    loadFragment(HomeFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_favorite -> {
                    loadFragment(FavoriteFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_firebase -> {
                    loadFragment(FirebaseFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_info -> {
                    loadFragment(InfoFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }

        // Carga el fragmento inicial
        loadFragment(HomeFragment())
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}


