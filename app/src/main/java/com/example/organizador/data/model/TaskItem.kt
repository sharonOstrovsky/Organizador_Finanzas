package com.example.organizador.data.model

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.organizador.R
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class TaskItem (
    var name: String,
    var desc: String,
    var dueTime: LocalTime?,
    var completeDate: LocalDate?,
    var id: UUID = UUID.randomUUID()
){
    fun isCompleted() = completeDate != null
    fun imageResource(): Int = if (isCompleted()) R.drawable.check_box else R.drawable.uncheck_box
    fun imageColor(context: Context): Int = if (isCompleted()) purple(context) else black(context)

    private fun purple(context: Context) = ContextCompat.getColor(context, R.color.purple_500)
    private fun black(context: Context) = ContextCompat.getColor(context, R.color.black)

}