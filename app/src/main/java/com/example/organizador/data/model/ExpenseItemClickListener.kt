package com.example.organizador.data.model

interface ExpenseItemClickListener {
    fun editExpenseItem(expenseItem: ExpenseItem)
    fun completeExpenseItem(expenseItem: ExpenseItem)

    fun deleteExpenseItem(expenseItem: ExpenseItem)
}