package com.example.organizador.ui.viewmodel

import androidx.lifecycle.*
import com.example.organizador.data.model.ExpenseItem
import com.example.organizador.data.model.TaskItem
import com.example.organizador.data.network.OrganizadorRepository
import com.example.organizador.ui.views.NewExpense
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID

class ExpenseViewModel(
    private val repository: OrganizadorRepository
): ViewModel() {

    var expenseItems : LiveData<List<ExpenseItem>> = repository.allExpenseItem.asLiveData()


    fun addExpenseItem(newExpense: ExpenseItem)= viewModelScope.launch{
        repository.insertExpenseItem(newExpense)
    }

    fun updateExpenseItem(expenseItem: ExpenseItem)= viewModelScope.launch{
        repository.updateExpenseItem(expenseItem)
    }
    fun setExpenseItem(expenseItem: ExpenseItem)= viewModelScope.launch{
        repository.updateExpenseItem(expenseItem)
    }

}

class ExpenseItemModelFactory(private val repository: OrganizadorRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseViewModel::class.java)){
            return ExpenseViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("Unkown class for View Model")
    }
}