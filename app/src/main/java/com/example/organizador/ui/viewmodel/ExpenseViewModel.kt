package com.example.organizador.ui.viewmodel

import androidx.lifecycle.*
import com.example.organizador.data.model.ExpenseItem
import com.example.organizador.data.network.OrganizadorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ExpenseViewModel(
    private val repository: OrganizadorRepository
) : ViewModel() {

    var expenseItems: LiveData<List<ExpenseItem>> = repository.allExpenseItem.asLiveData()
    private val _totalPriceLiveData = MutableLiveData<Double>()
    val totalPriceLiveData: LiveData<Double> = _totalPriceLiveData

    // Función para actualizar el total de la suma
    suspend fun updateTotalPrice() {

        val totalPrice = withContext(Dispatchers.IO) {
            repository.getTotalPrice()
        }
        _totalPriceLiveData.value = totalPrice

    }

    fun addExpenseItem(newExpense: ExpenseItem) = viewModelScope.launch {
        repository.insertExpenseItem(newExpense)
    }

    fun updateExpenseItem(expenseItem: ExpenseItem) = viewModelScope.launch {
        repository.updateExpenseItem(expenseItem)
    }

    fun setExpenseItem(expenseItem: ExpenseItem) = viewModelScope.launch {
        repository.updateExpenseItem(expenseItem)
    }

    fun deleteExpenseItem(expenseItem: ExpenseItem) = viewModelScope.launch {
        repository.deleteExpenseItem(expenseItem)
    }

    suspend fun getPrice(): Double {
        return repository.getTotalPrice()
    }


}

class ExpenseItemModelFactory(private val repository: OrganizadorRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseViewModel::class.java)) {
            return ExpenseViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("Unkown class for View Model")
    }
}