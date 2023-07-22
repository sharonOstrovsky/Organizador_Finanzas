package com.example.organizador.sharedPreferences

import android.content.Context
import android.content.SharedPreferences

class Prefs(val context: Context) {

    private val SHARED_NAME = "MY_PREF"

    private val storage: SharedPreferences =
        context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)

    fun saveIncome(income: String) {
        storage.edit().putString("income", income).apply()
    }

    fun getIncome(): String {
        return storage.getString("income", "")!!
    }

    fun saveTotalExpense(totalExpense: String) {
        storage.edit().putString("totalExpense", totalExpense).apply()
    }

    fun getTotalExpense(): String {
        return storage.getString("totalExpense", "")!!
    }

}