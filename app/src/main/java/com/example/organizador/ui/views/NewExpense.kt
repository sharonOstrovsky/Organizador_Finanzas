package com.example.organizador.ui.views

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.organizador.databinding.FragmentNewExpenseBinding
import com.example.organizador.data.model.ExpenseItem
import com.example.organizador.data.network.OrganizadorRepository
import com.example.organizador.ui.viewmodel.ExpenseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class NewExpense(var expenseItem: ExpenseItem?) : BottomSheetDialogFragment(){

    private lateinit var binding: FragmentNewExpenseBinding
    private lateinit var expenseViewModel: ExpenseViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        if(expenseItem != null){
            binding.expenseTitle.text = "Edit Expense"
            val editable = Editable.Factory.getInstance()
            binding.descriptionEditText.text = editable.newEditable(expenseItem!!.description)
            binding.priceEditText.text = editable.newEditable(expenseItem!!.price.toString())

        }else{
            binding.expenseTitle.text = "New Expense"
        }

        expenseViewModel = ViewModelProvider(activity).get(ExpenseViewModel::class.java)
        binding.saveButton.setOnClickListener {
            println(binding.descriptionEditText.text)
            saveAcction()
        }


    }

    private fun saveAcction(){
        val description = binding.descriptionEditText.text.toString()
        val price = binding.priceEditText.text.toString().toDouble()

        if(expenseItem == null){
            val newExpense = ExpenseItem(description, price)
            expenseViewModel.addExpenseItem(newExpense)
        }else{
            expenseItem!!.description = description
            expenseItem!!.price = price
            expenseViewModel.updateExpenseItem(expenseItem!!)
        }
        lifecycleScope.launch {
            expenseViewModel.updateTotalPrice()
        }

        binding.descriptionEditText.setText("")
        binding.priceEditText.setText("")

        dismiss()
    }



}

