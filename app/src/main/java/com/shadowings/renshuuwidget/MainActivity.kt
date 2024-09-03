package com.shadowings.renshuuwidget

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.shadowings.renshuuwidget.ui.theme.RenshuuWidgetTheme
import com.google.gson.Gson
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RenshuuWidgetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainComponent()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val workManager = WorkManager.getInstance(applicationContext)

        workManager.enqueueUniquePeriodicWork(
            "widget refresh",
            ExistingPeriodicWorkPolicy.UPDATE,
            PeriodicWorkRequest
                .Builder(MyWorker::class.java, 15L, TimeUnit.MINUTES)
                .build()
        )

        MainScope().launch {
            GlanceAppWidgetManager(this@MainActivity).getGlanceIds(WidgetSmallRow::class.java)
                .forEach { id ->
                    refreshWidget(this@MainActivity, id)
                }
        }
    }
}

internal val widgetKey = stringPreferencesKey("widget-key")

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
                        WidgetSmallRow().update(context, glanceId)
                    }
                }
            }
        }
    }
}