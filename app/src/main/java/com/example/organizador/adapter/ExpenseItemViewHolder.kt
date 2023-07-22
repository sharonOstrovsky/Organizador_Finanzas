package com.example.organizador.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.organizador.data.model.ExpenseItem
import com.example.organizador.data.model.ExpenseItemClickListener
import com.example.organizador.databinding.ItemExpenseBinding


class ExpenseItemViewHolder(
    private var context: Context,
    private var binding: ItemExpenseBinding,
    private val clickListener: ExpenseItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bindExpenseItem(expenseItem: ExpenseItem){
        println("ACA ${ expenseItem.description }")
        binding.descriptionTextView.text = expenseItem.description
        binding.priceTextView.text = expenseItem.price.toString()

        binding.completeButtonExpense.setOnClickListener {
            clickListener.completeExpenseItem(expenseItem)
        }
        binding.expenseCellContainer.setOnClickListener {
            clickListener.editExpenseItem(expenseItem)
        }
        binding.deleteButton.setOnClickListener {
            clickListener.deleteExpenseItem(expenseItem)
        }

    }
}

