package com.example.organizador.data.model

import java.util.*


class ExpenseItem (
    var description: String,
    var price: Double,
    var id: UUID = UUID.randomUUID()
)