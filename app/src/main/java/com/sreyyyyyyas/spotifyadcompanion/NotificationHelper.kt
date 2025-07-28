package com.sreyyyyyyas.spotifyadcompanion

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat

object NotificationHelper {

    const val CHANNEL_ID = "filler_playback_channel"
    const val NOTIFICATION_ID = 101

    fun createNotification(context: Context, songTitle: String): Notification {
        // Create custom layout for the notification
        val remoteViews = RemoteViews(context.packageName, R.layout.notification_filler_controls)
        remoteViews.setTextViewText(R.id.notification_song_title, songTitle)

        // Set up PendingIntents for each control
        val prevIntent = Intent(context, FillerPlaybackService::class.java).apply {
            action = "ACTION_PREVIOUS"
        }
        val playPauseIntent = Intent(context, FillerPlaybackService::class.java).apply {
            action = "ACTION_PLAY_PAUSE"
        }
        val skipIntent = Intent(context, FillerPlaybackService::class.java).apply {
            action = "ACTION_SKIP"
        }

        val prevPendingIntent = PendingIntent.getService(context, 0, prevIntent, PendingIntent.FLAG_IMMUTABLE)
        val playPausePendingIntent = PendingIntent.getService(context, 1, playPauseIntent, PendingIntent.FLAG_IMMUTABLE)
        val skipPendingIntent = PendingIntent.getService(context, 2, skipIntent, PendingIntent.FLAG_IMMUTABLE)

        // Assign intents to controls
        remoteViews.setOnClickPendingIntent(R.id.btn_prev, prevPendingIntent)
        remoteViews.setOnClickPendingIntent(R.id.btn_play_pause, playPausePendingIntent)
        remoteViews.setOnClickPendingIntent(R.id.btn_skip, skipPendingIntent)

        // Create Notification Channel if needed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "Filler Playback", NotificationManager.IMPORTANCE_LOW).apply {
                description = "Filler song playback controls"
                enableLights(false)
                enableVibration(false)
                lightColor = Color.BLUE
                setSound(null, null)
            }
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        // Build the notification
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_play) // Use one of your default icons
            .setCustomContentView(remoteViews)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .build()
    }
}
