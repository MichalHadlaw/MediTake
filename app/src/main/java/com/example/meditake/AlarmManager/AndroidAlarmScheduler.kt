package com.example.meditake.AlarmManager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.meditake.ToTakeViewModel
import com.example.meditake.db.ToTake
import com.example.meditake.notifications.ToTakeNotifivcationReciver
import java.time.ZoneId

class AndroidAlarmScheduler(
    private val context: Context
): alarmSchedjuller{


    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedjule(item: AlarmItem) {
        val alarmTimeMillis = item.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000
        Log.d("AlarmScheduler", "Ustawiam alarm na: ${item.time}, czas w milisekundach: $alarmTimeMillis")
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            alarmTimeMillis,
            AlarmManager.INTERVAL_DAY,
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                Intent(context, ToTakeNotifivcationReciver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }


    override fun cancle(item: AlarmItem) {
        val intent = Intent(context, ToTakeNotifivcationReciver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
}