package com.example.meditake.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ToTakeNotifivcationReciver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val notificationService = ToTakeNotificationService(context)
        val notificationType = intent?.getStringExtra("NOTIFICATION_TYPE")

        when (notificationType) {
            "REPEATED_REMINDER" -> {
                val title = intent.getStringExtra("NOTIFICATION_TITLE") ?: "Przypomnienie"
                notificationService.showNotification(title)
            }
            else -> {
                notificationService.showNotification("Nie zapomnij o Lekachj! Sprawdź swoją aplikacje MediTake")
            }
        }
    }
}