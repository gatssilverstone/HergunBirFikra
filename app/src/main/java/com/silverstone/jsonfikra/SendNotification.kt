package com.silverstone.jsonfikra

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import kotlin.random.Random


const val CHANNEL_ID = "my_app_channel"
    const val NOTIFICATION_ID = 1



fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "My App Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

fun sendNotification(context: Context) {
    if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS)
        == PackageManager.PERMISSION_GRANTED){
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(context)
        }

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val sloganList = listOf(
            "Hadi biraz yüzün gülsün 😊",
            "Günün stresini atacak fıkra geldi 😄",
            "Gülme saatin geldi 😁",
            "Gününü fıkra ile renklendir 😃",
            "Kahkaha zamanı! 😆",
            "Stres at, gülmeye başla 😂",
            "Günlük fıkra ile enerjini topla 😁",
            "Haydi, güzel bir gülme seansı yapalım 😄",
            "Günün fıkrası sizi bekliyor 😊",
            "Fıkra saatini kaçırma! 😁",
            "Mizahın seni rahatlatmasına izin ver 😃",
            "Günün stresini fıkra ile unut 😆",
            "Güldüren fıkralar burada 😂",
            "Gülümse, yeni bir fıkra var 😊",
            "Gününe güzel bir eğlence kat 😁",
            "Gülme zamanı! 😄",
            "Güldükçe yaşa 😃",
            "Fıkra zamanıııı 😆",
            "Kahkaha terapisi başlasın 😂",
            "Hayatı biraz daha eğlenceli hale getir 😁",
            "HOOOP HEMŞERİM, kahkaha saatin geldi 😂",
            "Bi İngiliz, bi Fransız, bi da bizum Temel 😂",
        )
        val random = Random(System.currentTimeMillis())
        val message = sloganList[random.nextInt(sloganList.size)]



        val emoji = "\uD83D\uDE04"
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Günün fıkrası hazır! $emoji")
            .setContentText(message)
            .setSmallIcon(R.drawable.icon)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

}
