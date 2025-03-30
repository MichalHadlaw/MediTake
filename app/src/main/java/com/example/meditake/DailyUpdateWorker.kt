package com.example.meditake

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.meditake.Main.MainAplication
import com.example.meditake.db.ToTakeDataBase

class DailyUpdateWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        // Uzyskujemy dostęp do bazy danych
        val database = ToTakeDataBase.getDatabase(applicationContext)

        // Uzyskujemy DAO z bazy
        val toTakeDao = database.getToTakeDao()

        // Wywołanie metody increaseTookOnTime, która będzie działać na danych
        val allToTakes = toTakeDao.getAllToTakeDirect() // Teraz mamy dostęp do danych bez LiveData
        Log.d("DailyUpdateWorker", "Liczba pobranych leków: ${allToTakes.size}")
        if (allToTakes.isEmpty()) {
            Log.d("DailyUpdateWorker", "Brak danych do przetworzenia")
        }
        allToTakes.forEach { item ->
            val updatedItem = if (item.isChecked) {
                Log.d("increaseTookOnTime", "Zaktualizowanie leku: ${item.title}, tookOnTime: ${item.tookOnTime + 1}")
                item.copy(tookOnTime = item.tookOnTime + 1, remainingDoses = (item.remainingDoses - item.dose.toInt()).coerceAtLeast(0), isChecked = false)
            } else {
                Log.d("increaseTookOnTime", "Lek nie był zaznaczony: ${item.title}")
                item.copy(notTaken = item.notTaken + 1)
            }
            toTakeDao.updateToTake(updatedItem)
        }

        return Result.success()
    }
}