package com.example.meditake.Main

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.room.Room
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.meditake.DailyUpdateWorker
import com.example.meditake.db.ToTakeDataBase
import com.example.meditake.notifications.ToTakeNotificationService
import java.util.Calendar
import java.util.concurrent.TimeUnit
import java.time.Duration

class MainAplication : Application() {

    companion object{
        const val CHANNEL_ID = "MediTakeNotificationChannel"
        lateinit var  totakeDatabase: ToTakeDataBase
    }


    override fun onCreate() {
        super.onCreate()
        totakeDatabase = Room.databaseBuilder(
            applicationContext,
            ToTakeDataBase::class.java,
            ToTakeDataBase.NAME
        )
            .build()
        createNotificationChannel()
        setupDailyUpdateWork()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
              ToTakeNotificationService.TO_TAKE_CHANNEL_ID,
                "ToTake",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for MediTake reminders"
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setupDailyUpdateWork() {
        // Tworzymy zaplanowaną pracę, która będzie uruchamiana co 24 godziny
        val workRequest = PeriodicWorkRequestBuilder<DailyUpdateWorker>(24, TimeUnit.HOURS)
            .setInitialDelay(calculateInitialDelay())  // Ustawienie opóźnienia na 23:59
            .build()

        // Rejestrujemy pracę z WorkManager
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "DailyUpdateWork",  // Unikalna nazwa pracy
            ExistingPeriodicWorkPolicy.KEEP,  // Nie nadpisuj istniejącej pracy
            workRequest
        )
    }

    // Oblicza opóźnienie do najbliższego 23:59
    private fun calculateInitialDelay(): java.time.Duration {
        val now = Calendar.getInstance()
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 0)
            if (before(now)) add(Calendar.DAY_OF_YEAR, 1)
        }
        // Oblicz różnicę w milisekundach
        val delayMillis = target.timeInMillis - now.timeInMillis

        // Przekształcamy delayMillis do Duration
        return Duration.ofMillis(delayMillis)
    }

}