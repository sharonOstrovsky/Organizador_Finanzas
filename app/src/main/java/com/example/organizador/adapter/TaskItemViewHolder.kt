package com.example.organizador.adapter

import android.content.Context
import android.graphics.Paint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.organizador.databinding.TaskItemCellBinding
import com.example.organizador.data.model.TaskItem
import com.example.organizador.data.model.TaskItemClickListener
import java.time.format.DateTimeFormatter

class TaskItemViewHolder(
    private var context: Context,
    private var binding: TaskItemCellBinding,
    private val clickListener: TaskItemClickListener
) : RecyclerView.ViewHolder(binding.root){

    @RequiresApi(Build.VERSION_CODES.O)
    val timeFormat = DateTimeFormatter.ofPattern("HH:mm")
    @RequiresApi(Build.VERSION_CODES.O)
    val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    @RequiresApi(Build.VERSION_CODES.O)
    fun bindTaskItem(taskItem: TaskItem){
        binding.name.text = taskItem.name
        binding.description.text = taskItem.desc
        if(taskItem.isCompleted()){
            binding.name.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.dueTime.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.dueDate.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.description.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.descriptionTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }

        binding.completeButton.setImageResource(taskItem.imageResource())
        binding.completeButton.setColorFilter(taskItem.imageColor(context))

        binding.completeButton.setOnClickListener {
            clickListener.completeTaskItem(taskItem)
        }
        binding.taskCellContainer.setOnClickListener {
            clickListener.editTaskItem(taskItem)
        }
        binding.deleteButton.setOnClickListener {
            clickListener.deleteTaskItem(taskItem)
        }

        if(taskItem.dueTime() != null){
            binding.dueTime.text = timeFormat.format(taskItem.dueTime())
        }else{
            binding.dueTime.text = ""
        }
        if(taskItem.dueDate() != null){
            binding.dueDate.text = dateFormat.format(taskItem.dueDate())
        }else{
            binding.dueDate.text = ""
        }

    }
}