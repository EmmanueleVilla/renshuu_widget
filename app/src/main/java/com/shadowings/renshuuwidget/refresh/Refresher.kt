package com.shadowings.renshuuwidget.refresh

import android.content.Context
import android.util.Log
import androidx.glance.GlanceId
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.google.gson.Gson
import com.shadowings.renshuuwidget.BuildConfig
import com.shadowings.renshuuwidget.main.fetchSchedule
import com.shadowings.renshuuwidget.main.widgetKey
import com.shadowings.renshuuwidget.models.ApiUsage
import com.shadowings.renshuuwidget.models.NewTerms
import com.shadowings.renshuuwidget.models.Schedule
import com.shadowings.renshuuwidget.models.ScheduleData
import com.shadowings.renshuuwidget.models.Terms
import com.shadowings.renshuuwidget.models.Today
import com.shadowings.renshuuwidget.utils.logIfDebug
import com.shadowings.renshuuwidget.widget.WidgetSmallRowComponent
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

fun refreshWidget(context: Context, glanceId: GlanceId) {
    MainScope().launch {
        logIfDebug("[WidgetRefreshWorker] refreshing widget: $glanceId")
        val prefs = context.getSharedPreferences("RWPrefs", Context.MODE_PRIVATE)
        val cachedKey = prefs.getString("api_key", null)
        logIfDebug("[WidgetRefreshWorker] api key: $cachedKey")
        cachedKey?.let { key ->
            logIfDebug("[WidgetRefreshWorker] fetching schedule for widget: $glanceId")
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
        } ?: run {
            logIfDebug("Key is null")
            if (BuildConfig.DEBUG) {
                logIfDebug("Updating in DEBUG mode")
                updateAppWidgetState(
                    context = context,
                    definition = PreferencesGlanceStateDefinition,
                    glanceId = glanceId
                ) { preferences ->
                    preferences.toMutablePreferences()
                        .apply {
                            this[widgetKey] = Gson().toJson(
                                ScheduleData(
                                    apiUsage = ApiUsage(
                                        callsToday = 0,
                                        dailyAllowance = 0
                                    ),
                                    schedules = listOf(
                                        Schedule(
                                            id = "0",
                                            name = "Words for beginner Japanese",
                                            isFrozen = 0,
                                            today = Today(
                                                review = 999,
                                                new = 999,
                                            ),
                                            newTerms = NewTerms(
                                                todayCount = 12,
                                                rollingWeekCount = 120
                                            ),
                                            upcoming = listOf(),
                                            terms = Terms(
                                                totalCount = 999,
                                                studiedCount = 999,
                                                hiddenCount = 999,
                                                unstudiedCount = 999
                                            )
                                        ),
                                        Schedule(
                                            id = "0",
                                            name = "Kanji for beginner Japanese",
                                            isFrozen = 0,
                                            today = Today(
                                                review = 999,
                                                new = 999,
                                            ),
                                            newTerms = NewTerms(
                                                todayCount = 12,
                                                rollingWeekCount = 120
                                            ),
                                            upcoming = listOf(),
                                            terms = Terms(
                                                totalCount = 999,
                                                studiedCount = 999,
                                                hiddenCount = 999,
                                                unstudiedCount = 999
                                            )
                                        )
                                    )
                                )
                            )
                        }
                }
                WidgetSmallRowComponent().update(context, glanceId)
            }
        }
    }
}
