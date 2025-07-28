package com.sreyyyyyyas.spotifyadcompanion

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MediaNotificationManager(private val context: Context) {

    private val mediaSession = MediaSessionCompat(context, "FillerSession")

    init {
        mediaSession.setFlags(
            MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or
                    MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
        )
        mediaSession.isActive = true
    }

    fun updateNotification(songTitle: String, isPlaying: Boolean) {
        // Playback state setup
        val stateBuilder = PlaybackStateCompat.Builder()
            .setActions(
                PlaybackStateCompat.ACTION_PLAY or
                        PlaybackStateCompat.ACTION_PAUSE or
                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT or
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
            )
            .setState(
                if (isPlaying) PlaybackStateCompat.STATE_PLAYING else PlaybackStateCompat.STATE_PAUSED,
                0L,
                1.0f
            )
        mediaSession.setPlaybackState(stateBuilder.build())

        // Metadata
        val metadata = MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, songTitle)
            .build()
        mediaSession.setMetadata(metadata)

        // Notification
        val notification = NotificationCompat.Builder(context, FillerPlaybackService.CHANNEL_ID)
            .setContentTitle(songTitle)
            .setContentText("Playing filler")
            .setSmallIcon(R.drawable.ic_music_note)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_foreground))
            .addAction(
                R.drawable.ic_skip_previous, "Back",
                getActionIntent(FillerPlaybackService.ACTION_PREV)
            )
            .addAction(
                if (isPlaying)
                    NotificationCompat.Action(
                        R.drawable.ic_pause, "Pause",
                        getActionIntent(FillerPlaybackService.ACTION_PAUSE)
                    )
                else
                    NotificationCompat.Action(
                        R.drawable.ic_play, "Play",
                        getActionIntent(FillerPlaybackService.ACTION_PLAY)
                    )
            )
            .addAction(
                R.drawable.ic_skip_next, "Next",
                getActionIntent(FillerPlaybackService.ACTION_NEXT)
            )
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSession.sessionToken)
                    .setShowActionsInCompactView(0, 1, 2)
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOnlyAlertOnce(true)
            .setOngoing(isPlaying)
            .build()

        NotificationManagerCompat.from(context)
            .notify(FillerPlaybackService.NOTIFICATION_ID, notification)
    }

    private fun getActionIntent(action: String): PendingIntent {
        val intent = Intent(context, FillerPlaybackService::class.java).apply {
            this.action = action
        }
        return PendingIntent.getService(
            context,
            action.hashCode(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }
}
