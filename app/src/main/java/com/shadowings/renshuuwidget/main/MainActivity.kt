package com.shadowings.renshuuwidget.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.shadowings.renshuuwidget.ui.theme.RenshuuWidgetTheme
import com.shadowings.renshuuwidget.widget.WidgetRefreshWorker
import com.shadowings.renshuuwidget.widget.WidgetSmallRow
import com.shadowings.renshuuwidget.widget.refreshWidget
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
                    mainComponent()
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
                .Builder(WidgetRefreshWorker::class.java, 15L, TimeUnit.MINUTES)
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