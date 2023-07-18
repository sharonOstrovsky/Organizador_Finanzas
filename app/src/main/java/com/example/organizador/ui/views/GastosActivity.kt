package com.example.organizador.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.organizador.adapter.ExpenseAdapter
import com.example.organizador.adapter.TaskItemAdapter
import com.example.organizador.data.model.ExpenseItem
import com.example.organizador.data.model.ExpenseItemClickListener
import com.example.organizador.databinding.ActivityGastosBinding
import com.example.organizador.ui.viewmodel.ExpenseViewModel


class GastosActivity : AppCompatActivity(), ExpenseItemClickListener {

    private lateinit var binding: ActivityGastosBinding
    private lateinit var expenseViewModel: ExpenseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGastosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        expenseViewModel = ViewModelProvider(this).get(ExpenseViewModel::class.java)
        binding.addButton.setOnClickListener {
            NewExpense(null).show(supportFragmentManager, "newExpenseTag")
        }

        setRecyclerView()

    }

    private fun setRecyclerView() {
        val gastosActivity = this
        expenseViewModel.expenseItems.observe(this) {
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = ExpenseAdapter(it!!, gastosActivity)
            }
        }
    }

    override fun editExpenseItem(expenseItem: ExpenseItem) {
        NewExpense(expenseItem).show(supportFragmentManager, "newExpenseTag")
    }

    override fun completeExpenseItem(expenseItem: ExpenseItem) {
        expenseViewModel.setExpenseItem(expenseItem)
    }


}