package com.example.organizador.ui.views


import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.organizador.OrganizadorApplication
import com.example.organizador.R
import com.example.organizador.adapter.ExpenseAdapter
import com.example.organizador.data.model.ExpenseItem
import com.example.organizador.data.model.ExpenseItemClickListener
import com.example.organizador.data.model.TaskItem
import com.example.organizador.databinding.ActivityGastosBinding
import com.example.organizador.ui.viewmodel.ExpenseItemModelFactory
import com.example.organizador.ui.viewmodel.ExpenseViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch


class GastosActivity : AppCompatActivity(), ExpenseItemClickListener {

    private lateinit var binding: ActivityGastosBinding

    private lateinit var bottomNavigationView: BottomNavigationView


    private val expenseViewModel: ExpenseViewModel by viewModels {
        ExpenseItemModelFactory((application as OrganizadorApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGastosBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.addButton.setOnClickListener {
            NewExpense(null).show(supportFragmentManager, "newExpenseTag")
        }

        setRecyclerView()
        //getPrice()


        bottonNavigation()



        expenseViewModel.totalPriceLiveData.observe(this) { totalPrice ->
            binding.price.text =
                totalPrice.toString()
        }

        lifecycleScope.launch {
            expenseViewModel.updateTotalPrice()
        }


    }

    fun bottonNavigation(){
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.expense
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
                else -> false
            }
        }
    }


    fun redirectGastos(){
        val intent = Intent(this, GastosActivity::class.java)
        startActivity(intent)
    }
    fun redirectToDo(){
        val intent = Intent(this, ToDoListActivity::class.java)
        startActivity(intent)
    }

    fun getPrice() {
        lifecycleScope.launch {
            val totalPrice = expenseViewModel.getPrice()
            binding.price.text = totalPrice.toString()
        }
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

    override fun deleteExpenseItem(expenseItem: ExpenseItem) {
        expenseViewModel.deleteExpenseItem(expenseItem)
        lifecycleScope.launch {
            expenseViewModel.updateTotalPrice()
        }
    }



}