package com.example.organizador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.organizador.databinding.ActivityGastosBinding


class GastosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGastosBinding
    val expenseList = mutableListOf<Expense>()
    private lateinit var adapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGastosBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ExpenseAdapter(expenseList)
        recyclerView.adapter = adapter

        agregarGasto()

    }

    fun agregarGasto(){

        binding.addButton.setOnClickListener {
            val description = binding.descriptionEditText.text.toString()
            val price = binding.priceEditText.text.toString().toDoubleOrNull()

            if (description.isNotEmpty() && price != null) {
                val newExpense = Expense(description, price)
                expenseList.add(newExpense)
                clearInputFields()
                binding.recyclerView.adapter?.notifyDataSetChanged()

            } else {
                Toast.makeText(this, "Ingrese una descripción y un precio válido", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun clearInputFields() {
        binding.descriptionEditText.text.clear()
        binding.priceEditText.text.clear()
        Toast.makeText(this, "GUARDADO", Toast.LENGTH_SHORT).show()
    }



}