package com.shadowings.renshuuwidget.refresh

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.google.gson.Gson
import com.shadowings.renshuuwidget.R
import com.shadowings.renshuuwidget.main.fetchSchedule
import com.shadowings.renshuuwidget.main.themeKey
import com.shadowings.renshuuwidget.main.widgetKey
import com.shadowings.renshuuwidget.utils.logIfDebug
import com.shadowings.renshuuwidget.widget.WidgetSmallRowComponent
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

fun refreshWidget(context: Context, glanceId: GlanceId) {
    MainScope().launch {
        val prefs = context.getSharedPreferences("RWPrefs", Context.MODE_PRIVATE)

        val cachedKey = prefs.getString("api_key", null)
        val selectedThemeKey = context.getString(R.string.selected_theme_key)
        val theme = prefs.getInt(selectedThemeKey, 0)

        cachedKey?.let { key ->
            fetchSchedule(context, key) {
                if (it != null) {
                    logIfDebug(
                        "Received schedule: ${
                            Gson().toJson(it)
                        }"
                    )
                    MainScope().launch {
                        updateAppWidgetState(
                            context = context,
                            definition = PreferencesGlanceStateDefinition,
                            glanceId = glanceId
                        ) { preferences ->
                            preferences.toMutablePreferences()
                                .apply {
                                    this[widgetKey] = Gson().toJson(it)
                                    this[themeKey] = theme.toString()
                                }
                        }
                        WidgetSmallRowComponent().update(context, glanceId)
                    }
                }
            }
        }
    }
}
