package com.simple.simpleforgroundservice.foregroundservice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.simple.simpleforgroundservice.R

class ForegroundService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(this)
        } else {
            ""
        }

        val channelBuilder = NotificationCompat.Builder(
            this,
            channelId
        )

        val notification = channelBuilder.setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setContentTitle("Foreground Service is running")
            .setContentText("Welcome to foreground service")
            .build()

        startForeground(101, notification)

        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(context: Context): String {

        val channel_id = "foreground_Service_id"
        val channel_name = "foreground_service_channel"

        var channel = NotificationChannel(
            channel_id,
            channel_name,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            lightColor = Color.BLUE
            importance = NotificationManager.IMPORTANCE_HIGH
            lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        }

        val service = ContextCompat.getSystemService(context, ForegroundService::class.java)
            ?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        service.createNotificationChannel(channel)

        return channel_id
    }


}