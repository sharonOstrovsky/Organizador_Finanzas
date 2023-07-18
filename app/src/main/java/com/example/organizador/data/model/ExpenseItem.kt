package com.example.organizador.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "expense_item_table")
class ExpenseItem (
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "price") var price: Double,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
    )