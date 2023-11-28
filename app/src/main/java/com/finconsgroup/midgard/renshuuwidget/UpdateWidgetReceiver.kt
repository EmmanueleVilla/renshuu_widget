package com.finconsgroup.midgard.renshuuwidget

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.glance.appwidget.GlanceAppWidgetManager
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class UpdateWidgetReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        MainScope().launch {
            listOf(
                WidgetSmallRow::class.java,
            ).forEach {
                GlanceAppWidgetManager(context).getGlanceIds(it).forEach { id ->
                    refreshWidget(context, id)
                }
            }
        }
    }
}