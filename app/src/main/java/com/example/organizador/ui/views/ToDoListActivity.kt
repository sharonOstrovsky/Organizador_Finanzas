package com.example.organizador.ui.views

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.organizador.OrganizadorApplication
import com.example.organizador.adapter.TaskItemAdapter
import com.example.organizador.databinding.ActivityToDoListBinding
import com.example.organizador.data.model.TaskItem
import com.example.organizador.data.model.TaskItemClickListener
import com.example.organizador.ui.viewmodel.TaskItemModelFactory
import com.example.organizador.ui.viewmodel.TaskViewModel

class ToDoListActivity : AppCompatActivity(), TaskItemClickListener {

    private lateinit var binding: ActivityToDoListBinding
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


}