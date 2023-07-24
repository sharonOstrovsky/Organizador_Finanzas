package com.example.organizador.ui.views

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.organizador.OrganizadorApplication.Companion.prefs
import com.example.organizador.R

class AlarmNotification : BroadcastReceiver() {

    companion object{
        var NOTIFICATION_ID = prefs.getNotificationId()
    }


    override fun onReceive(context: Context, p1: Intent?) {
        createAlarmNotification(context)
    }

    private fun createAlarmNotification(context: Context) {

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val flag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, flag)


        val notification = NotificationCompat.Builder(context, ToDoListActivity.MY_CHANNEL_ID)
            .setSmallIcon(R.drawable.chanchi)
            .setContentTitle("Tarea Pendiente")
            .setContentText("Es momento de pagar!")
            //.setStyle(
            //    NotificationCompat.BigTextStyle()
            //        .bigText("Esta es una notificacion programada para enviarse en un cierto horario.")
            //)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, notification)


    }

}