package com.example.organizador.ui.views

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

class ToDoListActivity : AppCompatActivity(), TaskItemClickListener {

    private lateinit var binding: ActivityToDoListBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    private val taskViewModel: TaskViewModel by viewModels {
        TaskItemModelFactory((application as OrganizadorApplication).repository)
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