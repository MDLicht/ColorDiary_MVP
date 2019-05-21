package com.mdlicht.zb.colordiarymvp.util

import android.preference.PreferenceManager
import com.mdlicht.zb.colordiarymvp.common.App


object PreferenceUtil {
    private const val KEY_ALARM_SETTING = "DailyAlarm"
    private const val KEY_IS_ALARM_RUNNING = "IsAlarmRunning"

    fun setAlarmSetting(isAlarm: Boolean) {
        savePref(KEY_ALARM_SETTING, isAlarm)
    }

    fun getAlarmSetting(): Boolean {
        return readPref(KEY_ALARM_SETTING, true)
    }

    fun setIsAlarmRunning(isAlarmRunning: Boolean) {
        savePref(KEY_IS_ALARM_RUNNING, isAlarmRunning)
    }

    fun getIsAlarmRunning(): Boolean {
        return readPref(KEY_IS_ALARM_RUNNING, false)
    }

    fun readPref(preferenceName: String, defaultValue: Boolean): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getInstance())
        return sharedPreferences.getBoolean(preferenceName, defaultValue)
    }

    fun savePref(preferenceName: String, preferenceValue: Boolean) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getInstance())
        val editor = sharedPreferences.edit()
        editor.putBoolean(preferenceName, preferenceValue)
        editor.apply()
    }

    fun readPref(preferenceName: String, defaultValue: Int): Int {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getInstance())
        return sharedPreferences.getInt(preferenceName, defaultValue)
    }

    fun savePref(preferenceName: String, preferenceValue: Int) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getInstance())
        val editor = sharedPreferences.edit()
        editor.putInt(preferenceName, preferenceValue)
        editor.apply()
    }

    fun readPref(preferenceName: String, defaultValue: String): String? {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getInstance())
        return sharedPreferences.getString(preferenceName, defaultValue)
    }

    fun savePref(preferenceName: String, preferenceValue: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getInstance())
        val editor = sharedPreferences.edit()
        editor.putString(preferenceName, preferenceValue)
        editor.apply()
    }
}