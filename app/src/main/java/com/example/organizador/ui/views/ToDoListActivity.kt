package com.example.organizador.ui.views

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.organizador.OrganizadorApplication
import com.example.organizador.R
import com.example.organizador.adapter.TaskItemAdapter
import com.example.organizador.databinding.ActivityToDoListBinding
import com.example.organizador.data.model.TaskItem
import com.example.organizador.data.model.TaskItemClickListener
import com.example.organizador.ui.viewmodel.TaskItemModelFactory
import com.example.organizador.ui.viewmodel.TaskViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class ToDoListActivity : AppCompatActivity(), TaskItemClickListener {

    private lateinit var binding: ActivityToDoListBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    private val taskViewModel: TaskViewModel by viewModels {
        TaskItemModelFactory((application as OrganizadorApplication).repository)
    }

    companion object{
        const val MY_CHANNEL_ID = "myChannel"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToDoListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newTaskButton.setOnClickListener{
            NewTaskSheet(null).show(supportFragmentManager, "newTaskTag")
        }

        setRecyclerView()

        bottonNavigation()


    }

    fun bottonNavigation(){

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.todo
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.expense -> {
                    redirectGastos()
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

    private fun setRecyclerView() {
        val toDoActivity = this
        taskViewModel.taskItems.observe(this){
            binding.todoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TaskItemAdapter(it!!, toDoActivity)
            }
        }
    }

    override fun editTaskItem(taskItem: TaskItem) {
        NewTaskSheet(taskItem).show(supportFragmentManager, "newTaskTag")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun completeTaskItem(taskItem: TaskItem) {
        taskViewModel.setCompleted(taskItem)
    }

    override fun deleteTaskItem(taskItem: TaskItem) {
        taskViewModel.deleteTaskItem(taskItem)
    }




}