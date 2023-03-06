package com.example.myday.receiver

import android.R
import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.AlarmClock
import android.text.format.DateFormat
import androidx.core.app.AlarmManagerCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.myday.service.AlarmService
import com.example.myday.util.Constants
import io.karn.notify.Notify

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Constants.ACTION_SET_EXACT -> {
                showNotification(context, intent.getStringExtra(Constants.MESSAGE)!!, intent.getIntExtra(Constants.CHANNEL_ID, 0))
            }
        }
    }

    private fun showNotification(context: Context, message: String, channelId : Int) {
        val notification = NotificationCompat.Builder(context, "channel_id")
            .setSmallIcon(R.drawable.ic_menu_today)
            .setContentTitle("Пора выполнить")
            .setContentText(message)
            .build()
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(channelId, notification)
    }

}