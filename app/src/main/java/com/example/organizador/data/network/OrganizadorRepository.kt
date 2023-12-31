package com.example.organizador.data.network

import androidx.annotation.WorkerThread
import com.example.organizador.TaskItemDao
import com.example.organizador.data.model.ExpenseItem
import com.example.organizador.data.model.TaskItem
import com.example.organizador.database.ExpenseItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class OrganizadorRepository(
    private val taskItemDao: TaskItemDao,
    private val expenseItemDao: ExpenseItemDao
) {
    val allTaskItem: Flow<List<TaskItem>> = taskItemDao.allTaskItems()
    val allExpenseItem: Flow<List<ExpenseItem>> = expenseItemDao.allExpensesItems()


    @WorkerThread
    suspend fun insertTaskItem(taskItem: TaskItem){
        taskItemDao.insertTaskItem(taskItem)
    }

    @WorkerThread
    suspend fun updateTaskItem(taskItem: TaskItem){
        taskItemDao.updateTaskItem(taskItem)
    }

    @WorkerThread
    suspend fun deleteTaskItem(taskItem: TaskItem){
        taskItemDao.deleteTaskItem(taskItem)
    }

    @WorkerThread
    suspend fun insertExpenseItem(expenseItem: ExpenseItem){
        expenseItemDao.insertExpenseItem(expenseItem)
    }

    @WorkerThread
    suspend fun updateExpenseItem(expenseItem: ExpenseItem){
        expenseItemDao.updateExpenseItem(expenseItem)
    }

    @WorkerThread
    suspend fun deleteExpenseItem(expenseItem: ExpenseItem){
        expenseItemDao.deleteExpenseItem(expenseItem)
    }

    suspend fun getTotalPrice(): Double = withContext(Dispatchers.IO) {
        println("aaaaaaa ${expenseItemDao.getTotalPrice()}")
        expenseItemDao.getTotalPrice()
    }
}