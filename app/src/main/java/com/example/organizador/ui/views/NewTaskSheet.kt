package com.example.organizador.ui.views

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.organizador.OrganizadorApplication.Companion.prefs
import com.example.organizador.databinding.FragmentNewTaskSheetBinding
import com.example.organizador.data.model.TaskItem
import com.example.organizador.ui.viewmodel.TaskViewModel
import com.example.organizador.ui.views.AlarmNotification.Companion.NOTIFICATION_ID
import com.example.organizador.ui.views.ToDoListActivity.Companion.MY_CHANNEL_ID
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import kotlin.random.Random

class NewTaskSheet(var taskItem: TaskItem?) : BottomSheetDialogFragment()
{
    private lateinit var binding: FragmentNewTaskSheetBinding
    private lateinit var taskViewModel: TaskViewModel
    private var dueTime: LocalTime? = null
    private var dueDate: LocalDate? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        createChannel()

        if (taskItem != null)
        {
            binding.taskTitle.text = "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.name.text = editable.newEditable(taskItem!!.name)
            binding.desc.text = editable.newEditable(taskItem!!.desc)
            if(taskItem!!.dueTime() != null){
                dueTime = taskItem!!.dueTime()!!
                updateTimeButtonText()
            }
            if(taskItem!!.dueDate() != null){
                dueDate = taskItem!!.dueDate()!!
                updateDateButtonText()
            }
        }
        else
        {
            binding.taskTitle.text = "New Task"
        }

        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)
        binding.saveButton.setOnClickListener {
            saveAction()
        }
        binding.timePickerButton.setOnClickListener {
            openTimePicker()
        }
        binding.datePickerButton.setOnClickListener {
            openDatePicker()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun openDatePicker() {
        val currentDate = LocalDate.now()
        val year = currentDate.year
        val month = currentDate.monthValue - 1 // El DatePickerDialog usa meses indexados desde 0
        val day = currentDate.dayOfMonth
        if (dueDate== null)
            dueDate = LocalDate.now()

        val listener = DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
            dueDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDay) // Vuelve a 1-based
            updateDateButtonText()
        }

        val dialog = DatePickerDialog(requireContext(), listener, year, month, day)
        dialog.setTitle("Select Due Date")
        dialog.show()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateDateButtonText() {
        binding.datePickerButton.text = String.format("%02d/%02d/%04d", dueDate!!.dayOfMonth, dueDate!!.monthValue, dueDate!!.year)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun openTimePicker() {
        if(dueTime == null)
            dueTime = LocalTime.now()
        val listener = TimePickerDialog.OnTimeSetListener{ _, selectedHour, selectedMinute ->
            dueTime = LocalTime.of(selectedHour, selectedMinute)
            updateTimeButtonText()
        }
        val dialog = TimePickerDialog(activity, listener, dueTime!!.hour, dueTime!!.minute, true)
        dialog.setTitle("Task Due")
        dialog.show()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateTimeButtonText() {
        binding.timePickerButton.text = String.format("%02d:%02d",dueTime!!.hour,dueTime!!.minute)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewTaskSheetBinding.inflate(inflater,container,false)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveAction()
    {
        val name = binding.name.text.toString()
        val desc = binding.desc.text.toString()
        val dueTimeString = if(dueTime == null) null else TaskItem.timeFormatter.format(dueTime)
        val dueDateString = if(dueDate == null) null else TaskItem.dateFormatter.format(dueDate)
        if(taskItem == null)
        {
            val newTask = TaskItem(name,desc,dueTimeString,dueDateString,null)
            taskViewModel.addTaskItem(newTask)
        }
        else
        {
            taskItem!!.name = name
            taskItem!!.desc = desc
            taskItem!!.dueTimeString = dueTimeString
            taskItem!!.dueDateString = dueDateString
            taskViewModel.updateTaskItem(taskItem!!)
        }
        if(dueDate != null  && dueTime != null){
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, dueDate!!.year)
            calendar.set(Calendar.MONTH, dueDate!!.monthValue - 1) // Ajuste para el valor correcto del mes
            calendar.set(Calendar.DAY_OF_MONTH, dueDate!!.dayOfMonth)
            calendar.set(Calendar.HOUR_OF_DAY, dueTime!!.hour)
            calendar.set(Calendar.MINUTE, dueTime!!.minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            scheduleNotification(calendar.timeInMillis)
        }


        //id: Int?, name: String, desc: String, dueTime: LocalTime?
        binding.name.setText("")
        binding.desc.setText("")
        dismiss()
    }

    private fun createChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                MY_CHANNEL_ID,
                "MyChannel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "ORGANIZADOR"
            }
            val notificationManager: NotificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun scheduleNotification(timeInMillis: Long) {

        val intent = Intent(requireContext(), AlarmNotification::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        println("NOTIFICATION: $NOTIFICATION_ID")
        NOTIFICATION_ID++
        prefs.saveNotificationId(NOTIFICATION_ID)

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
        }
    }





}