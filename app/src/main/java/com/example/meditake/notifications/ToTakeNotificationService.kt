package com.example.meditake.notifications

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.meditake.Main.MainActivity
import com.example.meditake.R

class ToTakeNotificationService(private  val context: Context) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(message: String) {
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, TO_TAKE_CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_add_task_24)
            .setContentTitle("Alarm Notification")
            .setContentText(message)
            .setContentIntent(activityPendingIntent)
            .build()

        notificationManager.notify(1, notification)
    }
    fun scheduleNotificationAfterMinutes(title: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val times = listOf(5, 10, 15) // Powiadomienia po tych minutach

        times.forEach { minutes ->
            val intent = Intent(context, ToTakeNotifivcationReciver::class.java).apply {
                putExtra("NOTIFICATION_TITLE", title)
                putExtra("NOTIFICATION_TYPE", "REPEATED_REMINDER") // Nowy typ powiadomienia
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context, minutes, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val triggerTime = Calendar.getInstance().apply {
                add(Calendar.MINUTE, minutes)
            }.timeInMillis

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
        }
    }
    companion object {
        const val TO_TAKE_CHANNEL_ID = "alarm_channel"
    }
}