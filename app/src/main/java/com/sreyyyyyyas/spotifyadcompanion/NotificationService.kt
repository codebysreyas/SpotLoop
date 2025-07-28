package com.sreyyyyyyas.spotifyadcompanion

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.app.NotificationCompat

class NotificationService : Service() {

    companion object {
        const val NOTIFICATION_CHANNEL_ID_SERVICE = "spotifyadcompanion_service"
        const val NOTIFICATION_CHANNEL_NAME = "Spotify Ad Companion Background Service"
        const val ACTION_STOP = "STOP_SERVICE"
    }

    override fun onCreate() {
        super.onCreate()

        // Stop button intent
        val stopIntent = Intent(this, StopServiceBroadcastReceiver::class.java).apply {
            putExtra("Action", ACTION_STOP)
        }
        val stopPendingIntent = PendingIntent.getBroadcast(
            this, 0, stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val stopAction = NotificationCompat.Action.Builder(
            R.drawable.ic_baseline_close_24, "Stop", stopPendingIntent
        ).build()

        // Create channel if needed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID_SERVICE,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID_SERVICE)
            .setContentTitle("Spotify Ad Companion")
            .setContentText("Running in background to handle Spotify ads.")
            .setSmallIcon(R.drawable.mutify_logo_without_bg)
            .setContentIntent(mainPendingIntent())
            .addAction(stopAction)
            .setOngoing(true)
            .build()

        startForeground(101, notification)
        Toast.makeText(this, "Ad muting is active!", Toast.LENGTH_SHORT).show()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == ACTION_STOP) stopSelf()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Ad muting stopped", Toast.LENGTH_SHORT).show()
    }

    private fun mainPendingIntent(): PendingIntent {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        return PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    @Nullable
    override fun onBind(intent: Intent?): IBinder? = null
}
