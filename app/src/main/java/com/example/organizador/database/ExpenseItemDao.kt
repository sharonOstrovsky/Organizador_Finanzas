package com.example.organizador.database

import androidx.room.*
import com.example.organizador.data.model.ExpenseItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseItemDao {
    @Query("SELECT * FROM expense_item_table ORDER BY id ASC")
    fun allExpensesItems(): Flow<List<ExpenseItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpenseItem(expenseItem: ExpenseItem)
    @Update
    suspend fun updateExpenseItem(expenseItem: ExpenseItem)
    @Delete
    suspend fun deleteExpenseItem(expenseItem: ExpenseItem)
    @Query("SELECT SUM(price) FROM expense_item_table")
    fun getTotalPrice():Double

}

