package com.sreyyyyyyas.spotifyadcompanion

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class FillerPlaybackService : Service() {

    private var mediaPlayer: MediaPlayer? = null
    private val fillerUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3" // Replace with your filler URL

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundNotification()
        startFiller()

        return START_NOT_STICKY
    }

    private fun startFiller() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(fillerUrl)
                prepareAsync()
                setOnPreparedListener {
                    Log.d("FillerService", "🎶 Playing filler")
                    start()
                }
                setOnCompletionListener {
                    Log.d("FillerService", "✅ Filler ended")
                    stopSelf()
                }
                setOnErrorListener { _, what, extra ->
                    Log.e("FillerService", "❌ Error - $what : $extra")
                    stopSelf()
                    true
                }
            }
        }
    }

    private fun startForegroundNotification() {
        val channelId = "FILLER_CHANNEL_ID"
        val channelName = "Filler Music"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chan = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(chan)
        }

        val notification: Notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Spotify Ad Companion")
            .setContentText("🎵 Playing filler music...")
            .setSmallIcon(R.drawable.ic_music_note) // Replace with your icon
            .build()

        startForeground(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
        Log.d("FillerService", "🛑 Service destroyed")
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
