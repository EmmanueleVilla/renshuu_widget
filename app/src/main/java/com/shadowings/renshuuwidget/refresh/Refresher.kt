package com.shadowings.renshuuwidget.refresh

import android.content.Context
import android.util.Log
import androidx.glance.GlanceId
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.google.gson.Gson
import com.shadowings.renshuuwidget.main.fetchSchedule
import com.shadowings.renshuuwidget.main.widgetKey
import com.shadowings.renshuuwidget.widget.WidgetSmallRowComponent
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

fun refreshWidget(context: Context, glanceId: GlanceId) {
    MainScope().launch {
        val prefs = context.getSharedPreferences("RWPrefs", Context.MODE_PRIVATE)
        prefs.getString("api_key", null)?.let { key ->
            fetchSchedule(context, key) {
                if (it != null) {
                    Log.e("VECNA", Gson().toJson(it))
                    MainScope().launch {
                        updateAppWidgetState(
                            context = context,
                            definition = PreferencesGlanceStateDefinition,
                            glanceId = glanceId
                        ) { preferences ->
                            preferences.toMutablePreferences()
                                .apply {
                                    this[widgetKey] = Gson().toJson(it)
                                }
                        }
                        WidgetSmallRowComponent().update(context, glanceId)
                    }
                }
            }
        }
    }
}
