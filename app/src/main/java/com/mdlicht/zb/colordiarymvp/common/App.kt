package com.mdlicht.zb.colordiarymvp.common

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Handler
import com.mdlicht.zb.colordiarymvp.R
import com.mdlicht.zb.colordiarymvp.util.AlarmUtil
import com.tsengvn.typekit.Typekit
import java.util.*

class App : Application() {
    companion object {
        private lateinit var INSTANCE: App

        val feelColorMap: SortedMap<String, Int> = sortedMapOf()

        fun getInstance(): App {
            return INSTANCE
        }
    }

    external fun getNativeKey1(): String
    external fun getNativeKey2(): String

    init {
        System.loadLibrary("keys")
    }

    fun restartApp(delay: Int = 0) {
        Handler().postDelayed({
            val pm = packageManager
            val intent = pm.getLaunchIntentForPackage(packageName)
            intent?.let {
                val cn = it.component
                val mainIntent = Intent.makeRestartActivityTask(cn)
                startActivity(mainIntent)
                System.exit(0)
            }
        }, delay.toLong())
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        Typekit.getInstance()
            .addNormal(Typekit.createFromAsset(this, "NanumGothic.ttf"))
            .addBold(Typekit.createFromAsset(this, "NanumGothicBold.ttf"))
            .addCustom1(Typekit.createFromAsset(this, "NanumGothicLight.ttf"))

        val feel = resources.getStringArray(R.array.feelings)
        val feelColor = resources.getIntArray(R.array.feel_colors)
        for ((index, f) in feel.withIndex()) {
            feelColorMap[f] = feelColor[index]
        }

        AlarmUtil.startAlarm()

        val receiver = ComponentName(this, AlarmReceiver::class.java)
        packageManager.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }
}