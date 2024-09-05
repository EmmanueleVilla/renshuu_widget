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
import com.shadowings.renshuuwidget.ui.theme.RenshuuWidgetThemeComponent
import com.shadowings.renshuuwidget.refresh.WidgetRefreshWorker
import com.shadowings.renshuuwidget.widget.WidgetSmallRowComponent
import com.shadowings.renshuuwidget.refresh.refreshWidget
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

private const val REPEAT_TIME = 15L

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RenshuuWidgetThemeComponent {
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
                .Builder(WidgetRefreshWorker::class.java, REPEAT_TIME, TimeUnit.MINUTES)
                .build()
        )

        MainScope().launch {
            GlanceAppWidgetManager(this@MainActivity).getGlanceIds(WidgetSmallRowComponent::class.java)
                .forEach { id ->
                    refreshWidget(this@MainActivity, id)
                }
        }
    }
}

internal val widgetKey = stringPreferencesKey("widget-key")
