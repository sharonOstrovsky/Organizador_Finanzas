package com.example.organizador.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.organizador.data.model.ExpenseItem
import com.example.organizador.data.model.ExpenseItemClickListener
import com.example.organizador.databinding.ItemExpenseBinding

class ExpenseAdapter(
    private val expenseList: List<ExpenseItem>,
private val clickListener: ExpenseItemClickListener) :
    RecyclerView.Adapter<ExpenseItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        val binding = ItemExpenseBinding.inflate(itemView,parent,false)
        return ExpenseItemViewHolder(parent.context, binding, clickListener)
    }

    override fun onBindViewHolder(holder: ExpenseItemViewHolder, position: Int) {
        val currentExpense = expenseList[position]
        holder.bindExpenseItem(currentExpense)

    }

    override fun getItemCount(): Int {
        return expenseList.size
    }


}
