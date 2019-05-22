package com.mdlicht.zb.colordiarymvp.common

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.TaskStackBuilder
import com.mdlicht.zb.colordiarymvp.R
import com.mdlicht.zb.colordiarymvp.activity.MainActivity
import com.mdlicht.zb.colordiarymvp.util.AlarmUtil
import com.mdlicht.zb.colordiarymvp.util.AlarmUtil.ID_DAILY_8
import com.mdlicht.zb.colordiarymvp.util.PreferenceUtil


class AlarmReceiver : BroadcastReceiver() {
    companion object {
        const val KEY_ALARM = "com.mdlicht.zb.colordiary.ALARM"
    }

    private fun makeNotification(p0: Context?, p1: Intent?) {
        val builder = NotificationCompat.Builder(p0!!, ID_DAILY_8)
        builder.setSmallIcon(R.drawable.ic_happy).setContentTitle(p0.getString(R.string.app_name))
            .setContentText(p0.getString(R.string.noti_contents))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
        val intent = Intent(p0, MainActivity::class.java)
        val taskStack = TaskStackBuilder.create(p0!!).addParentStack(MainActivity::class.java).addNextIntent(intent)
        val pendingIntent = taskStack.getPendingIntent(
            0,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.setContentIntent(pendingIntent)
        val notiManager = p0.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notiManager.notify(0, builder.build())
    }

    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1?.action == "android.intent.action.BOOT_COMPLETED") {
            AlarmUtil.startAlarm(isForce = true)
        } else if (p1?.action == KEY_ALARM) {
            if(PreferenceUtil.getAlarmSetting()) {
                makeNotification(p0, p1)
            }
        }
    }
}