package com.mdlicht.zb.colordiarymvp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mdlicht.zb.colordiarymvp.R
import com.mdlicht.zb.colordiarymvp.util.PreferenceUtil
import kotlinx.android.synthetic.main.item_settings_switch.view.*
import kotlinx.android.synthetic.main.item_settings.view.tvTitle as tvTitle1

class SettingsRvAdapter(private val listener: OnSettingsItemClickListener, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val settingsList = context.resources.getStringArray(R.array.settings_title_list)

    interface OnSettingsItemClickListener {
        fun onBackUpClick()
        fun onImportClick()
    }

    enum class ViewType(val type: Int) {
        TYPE_NORMAL(0),
        TYPE_SWITCH(1)
    }

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            2 -> ViewType.TYPE_SWITCH.type
            else -> ViewType.TYPE_NORMAL.type
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return when(p1) {
            ViewType.TYPE_NORMAL.type -> {
                SettingsNormalViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_settings, p0, false))
            }
            ViewType.TYPE_SWITCH.type -> {
                SettingsSwitchViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_settings_switch, p0, false))
            }
            else -> throw Exception()
        }
    }

    override fun getItemCount(): Int {
        return settingsList.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        when(p0) {
            is SettingsNormalViewHolder -> {
                p0.itemView.apply {
                    val title = settingsList[p1]
                    this.tvTitle.text = title
                    this.setOnClickListener {
                        when (title) {
                            it.context.getString(R.string.settings_export) -> {
                                listener.onBackUpClick()
                            }
                            it.context.getString(R.string.settings_import) -> {
                                listener.onImportClick()
                            }
                        }
                    }
                }
            }
            is SettingsSwitchViewHolder -> {
                p0.itemView.apply {
                    this.tvTitle.text = settingsList[p1]
                    swBtn.isChecked = PreferenceUtil.getAlarmSetting()
                    swBtn.setOnCheckedChangeListener { _, b ->
                        PreferenceUtil.setAlarmSetting(b)
                    }
                }
            }
        }
    }

    class SettingsNormalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class SettingsSwitchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}