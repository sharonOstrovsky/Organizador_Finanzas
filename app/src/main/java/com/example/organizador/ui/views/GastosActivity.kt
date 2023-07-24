package com.example.organizador.ui.views


import android.app.UiModeManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Switch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.organizador.OrganizadorApplication
import com.example.organizador.OrganizadorApplication.Companion.prefs
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

    private var isDarkModeEnabled = false


    private val expenseViewModel: ExpenseViewModel by viewModels {
        ExpenseItemModelFactory((application as OrganizadorApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGastosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Gastos"


        binding.addButton.setOnClickListener {
            NewExpense(null).show(supportFragmentManager, "newExpenseTag")
        }

        setRecyclerView()

        bottonNavigation()

        expenseViewModel.totalPriceLiveData.observe(this) { totalPrice ->
            binding.price.text =
                totalPrice.toString()
            prefs.saveTotalExpense(totalPrice.toString())
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
    fun redirectMain(){
        val intent = Intent(this, MainActivity::class.java)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options, menu)


        val darkModeMenuItem = menu?.findItem(R.id.mode_switch)
        val darkModeSwitch = darkModeMenuItem?.actionView as Switch

        // Establecer el estado inicial del interruptor según el modo actual
        darkModeSwitch.isChecked = isDarkModeEnabled

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Verificar si el modo oscuro ha cambiado
            if (isChecked != isDarkModeEnabled) {
                isDarkModeEnabled = isChecked
                if (isChecked) {
                    enableDarkMode()
                } else {
                    disableDarkMode()
                }
            }
        }
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                redirectMain()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun enableDarkMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        delegate.applyDayNight()
    }

    private fun disableDarkMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        delegate.applyDayNight()
    }




}