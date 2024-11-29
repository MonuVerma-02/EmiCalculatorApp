package com.emi.calculator.firebase

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mpv.emi.calculator.R

class FirebaseMessaging : FirebaseMessagingService() {
    private val NOTIFICATION_CHANNEL_ID = "notification_id_01"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let {
            val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(remoteMessage.from)
                .setContentText(it.body)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build()
            val manager = NotificationManagerCompat.from(applicationContext)
            manager.notify(/*notification id*/0, notification)
        }
    }

    override fun onNewToken(token: String) {
    }
}
