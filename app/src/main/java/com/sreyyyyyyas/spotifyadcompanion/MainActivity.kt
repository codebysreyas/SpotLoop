package com.sreyyyyyyas.spotifyadcompanion

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.sreyyyyyyas.spotifyadcompanion.ui.Navigation
import com.sreyyyyyyas.spotifyadcompanion.ui.theme.SpotifyAdCompanionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ask user to enable Notification Listener
        startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))

        // ✅ Start foreground Notification Service
        val serviceIntent = Intent(this, NotificationService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }

        setContent {
            SpotifyAdCompanionTheme {
                Surface {
                    Navigation()
                }
            }
        }
    }
}
