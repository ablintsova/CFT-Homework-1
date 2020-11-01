package com.example.homework1

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit


class BgService : Service() {
    private companion object {
        const val DEFAULT_CHANNEL_ID = "0"
        const val REQUEST_CODE = 111
    }

    private val executorService = Executors.newSingleThreadScheduledExecutor()
    private val notificationManager: NotificationManager by lazy {
        getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }
    private lateinit var future: ScheduledFuture<*>
    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val handler = Handler(Looper.getMainLooper())

        startNotifications(handler)

        return START_STICKY
    }

    private fun startNotifications(handler: Handler) {
        var count = 0
        future = executorService.scheduleAtFixedRate({
            val notification = buildNotification(count)
            handler.post {
                notificationManager.notify(REQUEST_CODE, notification)
                count++
            }
        }, 0, 1, TimeUnit.SECONDS)
    }

    private fun buildNotification(count: Int): Notification {
        val activityIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,
            REQUEST_CODE,
            activityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)
        val text = "$count ${getManuls(count)}"

        return NotificationCompat.Builder(this, DEFAULT_CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Считаем манулов")
            .setContentText(text)
            .setNotificationSilent()
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel"
            val descriptionText = "default channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(DEFAULT_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun getManuls(manulCount: Int): String =
        if (manulCount % 100 in 11..14) {
            "манулов"
        } else {
            when (manulCount % 10) {
                1 -> "манул"
                2, 3, 4 -> "манула"
                else -> "манулов"
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        future.cancel(true)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as? NotificationManager
        notificationManager?.cancel(REQUEST_CODE)
    }

}