package com.example.organizador.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.organizador.data.model.ExpenseItem
import com.example.organizador.ui.views.NewExpense
import java.util.UUID

class ExpenseViewModel: ViewModel() {

    var expenseItems = MutableLiveData<MutableList<ExpenseItem>?>()

    init {
        expenseItems.value = mutableListOf()
    }

    fun addExpenseItem(newExpense: ExpenseItem){
        val list = expenseItems.value
        list!!.add(newExpense)
        expenseItems.postValue(list)
    }

    fun updateExpenseItem(description: String, price: Double, id: UUID){
        val list = expenseItems.value
        val expense = list!!.find { it.id == id }!!
        expense.description = description
        expense.price = price
    }

    fun setExpenseItem(expenseItem: ExpenseItem){
        val list = expenseItems.value
        val expense = list!!.find { it.id == expenseItem.id }!!
        expenseItems.postValue(list)
    }

}