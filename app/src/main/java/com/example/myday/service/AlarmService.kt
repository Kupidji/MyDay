package com.example.myday.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.myday.receiver.AlarmReceiver
import com.example.myday.util.Constants
import com.example.myday.util.RandomUtil

class AlarmService(private val context: Context) {
    private val alarmManager: AlarmManager? =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

    fun setExactAlarm(timeInMillis: Long, message : String, channelID : Int) {
        setAlarm(
            timeInMillis,
            getPendingIntent(
                channelID,
                getIntent().apply {
                    action = Constants.ACTION_SET_EXACT
                    putExtra(Constants.MESSAGE, message)
                    putExtra(Constants.CHANNEL_ID, channelID)
                    putExtra(Constants.EXTRA_EXACT_ALARM_TIME, timeInMillis)
                }
            )
        )
    }

    fun cancelNotification(timeInMillis: Long, message : String, channelID : Int) {
        if (timeInMillis != 0L) {
            cancelExactAlarm(
                getPendingIntent(
                    channelID,
                    getIntent().apply {
                        action = Constants.ACTION_SET_EXACT
                        putExtra(Constants.MESSAGE, message)
                        putExtra(Constants.CHANNEL_ID, channelID)
                        putExtra(Constants.EXTRA_EXACT_ALARM_TIME, timeInMillis)
                    }
                )
            )
        }
    }

    private fun getPendingIntent(channelID: Int, intent: Intent) =
        PendingIntent.getBroadcast(
            context,
            channelID,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )


    private fun setAlarm(timeInMillis: Long, pendingIntent: PendingIntent) {
        alarmManager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )
            }
        }
    }

    private fun cancelExactAlarm(pendingIntent: PendingIntent) {
        alarmManager?.cancel(pendingIntent)
    }

    private fun getIntent() = Intent(context, AlarmReceiver::class.java)

}