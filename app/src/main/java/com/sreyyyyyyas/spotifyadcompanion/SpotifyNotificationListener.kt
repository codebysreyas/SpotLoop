package com.sreyyyyyyas.spotifyadcompanion

import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class SpotifyNotificationListener : NotificationListenerService() {

    private var isAdPlaying = false

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        if (sbn.packageName != "com.spotify.music") return

        val extras = sbn.notification.extras
        val title = extras.getString("android.title") ?: ""
        val text = extras.getString("android.text") ?: ""

        Log.d("SpotifyListener", "📻 Notification - Title: $title, Text: $text")

        val isProbablyAd = title.contains("Advertisement", ignoreCase = true) ||
                text.contains("Spotify", ignoreCase = true) && text.contains("Ad", ignoreCase = true)

        if (isProbablyAd && !isAdPlaying) {
            isAdPlaying = true
            Log.d("SpotifyListener", "🚫 Ad Detected - Starting Filler")

            val intent = Intent(this, FillerPlaybackService::class.java)
            startService(intent)
        } else if (!isProbablyAd && isAdPlaying) {
            isAdPlaying = false
            Log.d("SpotifyListener", "✅ Ad Ended - Stopping Filler")

            stopService(Intent(this, FillerPlaybackService::class.java))
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        // Optional: handle cleanup if needed
    }
}
