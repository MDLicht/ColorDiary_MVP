package com.mdlicht.zb.colordiarymvp.util

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.mdlicht.zb.colordiarymvp.R
import com.mdlicht.zb.colordiarymvp.common.AlarmReceiver
import com.mdlicht.zb.colordiarymvp.common.App
import java.util.*

object AlarmUtil {
    const val ID_DAILY_8 = "Daily8"

    fun startAlarm(isForce: Boolean = false) {
        if(isForce || !PreferenceUtil.getIsAlarmRunning()) {
            createNotificationChannel()

            val alarmIntent = Intent(App.getInstance(), AlarmReceiver::class.java).apply {
                action = "com.mdlicht.zb.colordiary.ALARM"
            }
            val pendingIntent =
                PendingIntent.getBroadcast(App.getInstance(), 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val alarmTime = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 20)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                add(Calendar.DATE, 1)
            }

            val alarmManager = App.getInstance().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                alarmTime.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
            PreferenceUtil.setIsAlarmRunning(true)
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = App.getInstance().getString(R.string.notification_channel_name)
            val descriptionText = App.getInstance().getString(R.string.notification_channel_desc)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(ID_DAILY_8, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                App.getInstance().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}