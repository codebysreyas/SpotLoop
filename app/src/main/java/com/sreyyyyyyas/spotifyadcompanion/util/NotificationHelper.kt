package com.sreyyyyyyas.spotifyadcompanion.util

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import com.sreyyyyyyas.spotifyadcompanion.R

object NotificationHelper {
    fun createFillerNotification(context: Context): Notification {
        return NotificationCompat.Builder(context, "filler_channel")
            .setContentTitle("Filler Playing")
            .setContentText("Spotify ad detected. Playing something better.")
            .setSmallIcon(R.drawable.ic_music_note)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }
}
