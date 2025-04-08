package de.osca.android.networkservice.messaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import de.osca.android.networkservice.R
import java.util.Random

@AndroidEntryPoint
class MessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        showNotification(message, this)
    }

    private fun showNotification (message: RemoteMessage, context: Context) {
        val notificationId = Random().nextInt()

        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(Intent(Intent.ACTION_VIEW, message.data[NAMED_DEEPLINK_DIRECTION_EXTRA]?.toUri()))
            getPendingIntent(1234, PendingIntent.FLAG_IMMUTABLE)
        }

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(context, message.notification?.channelId ?: DEFAULT_NOTIFICATION_ID)
            .setSmallIcon(R.drawable.ic_circle)
            .setContentTitle(message.notification?.title ?: "---")
            .setContentText(message.notification?.body ?: "---")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val channel = NotificationChannel(
            message.notification?.channelId ?: DEFAULT_NOTIFICATION_ID, // id
            message.notification?.channelId ?: DEFAULT_NOTIFICATION_ID, // name
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(notificationId, builder.build())
    }

    companion object {
        const val DEFAULT_NOTIFICATION_ID = "Default"
        const val NAMED_DEEPLINK_DIRECTION_EXTRA = "deepLink"
    }
}