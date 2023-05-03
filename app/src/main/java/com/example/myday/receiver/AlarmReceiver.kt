package com.example.myday.receiver

import android.R
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.myday.util.Constants

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Constants.ACTION_SET_EXACT -> {
                showNotification(context, intent.getStringExtra(Constants.MESSAGE)!!, intent.getIntExtra(Constants.CHANNEL_ID, 0))
            }
        }
    }

    private fun showNotification(context: Context, message: String, channelID : Int) {
        val notification = NotificationCompat.Builder(context, Constants.TASK_NOTIFICATION)
            .setSmallIcon(R.drawable.ic_menu_today)
            .setContentTitle("Пора выполнить")
            .setContentText(message)
            .build()
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(channelID, notification)
    }

}