package com.silverstone.jsonfikra

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.TimeZone
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class DailyFikraWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        var fikra :Int
        do {
            fikra = Random.nextInt(1, 2559)
        } while (
            (fikra >= 90 && fikra <= 303) ||
            (fikra >= 600 && fikra <= 610) ||
            (fikra >= 436 && fikra <= 482)
        )
        val sharedFikra = applicationContext.getSharedPreferences("gunlukFikra", Context.MODE_PRIVATE)
        sharedFikra.edit().putInt("dailyFikra", fikra).apply()
        sendNotification(applicationContext)
        return Result.success()
    }
}

fun setDailyFikraWithWorkManager(context: Context) {
    val workRequest = OneTimeWorkRequestBuilder<DailyFikraWorker>()
        .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
        .build()

    WorkManager.getInstance(context).enqueueUniqueWork("dailyFikra",ExistingWorkPolicy.KEEP, workRequest)

}

private fun calculateInitialDelay(): Long {
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Istanbul"))
    calendar.set(Calendar.HOUR_OF_DAY, 20)
    calendar.set(Calendar.MINUTE, 31)
    val currentTimeMillis = System.currentTimeMillis()
    val scheduledTimeMillis = calendar.timeInMillis

    return if (scheduledTimeMillis <= currentTimeMillis) {
        scheduledTimeMillis + TimeUnit.DAYS.toMillis(1) - currentTimeMillis
    } else {
        scheduledTimeMillis - currentTimeMillis
    }
}