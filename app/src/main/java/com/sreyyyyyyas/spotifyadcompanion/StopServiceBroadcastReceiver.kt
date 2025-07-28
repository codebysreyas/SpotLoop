package com.sreyyyyyyas.spotifyadcompanion

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class StopServiceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val serviceIntent = Intent(context, NotificationService::class.java)
        context.stopService(serviceIntent)
    }
}
