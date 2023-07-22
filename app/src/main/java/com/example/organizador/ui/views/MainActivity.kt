package com.example.organizador.ui.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.organizador.R
import com.example.organizador.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.expense -> {
                    redirectGastos()
                    true
                }
                R.id.todo -> {
                    redirectToDo()
                    true
                }
                R.id.graphPie ->{
                    redirectGrafico()
                    true
                }
                else -> false
            }
        }
    }

    fun redirectGrafico(){
        val intent = Intent(this, GraficoActivity::class.java)
        startActivity(intent)
    }
    fun redirectGastos(){
        val intent = Intent(this, GastosActivity::class.java)
        startActivity(intent)
    }

    fun redirectToDo(){
        val intent = Intent(this, ToDoListActivity::class.java)
        startActivity(intent)
    }
}