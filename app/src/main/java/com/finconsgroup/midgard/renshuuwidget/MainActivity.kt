package com.finconsgroup.midgard.renshuuwidget

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.finconsgroup.midgard.renshuuwidget.ui.theme.RenshuuWidgetTheme
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
            ExistingPeriodicWorkPolicy.REPLACE,
            PeriodicWorkRequest
                .Builder(MyWorker::class.java, 30L, TimeUnit.MINUTES)
                .build()
        )

        MainScope().launch {
            GlanceAppWidgetManager(this@MainActivity).getGlanceIds(WidgetSmallRow::class.java)
                .forEach { id ->
                    refreshWidget(this@MainActivity, id)
                }
        }
        //val browserIntent =
            //Intent(Intent.ACTION_VIEW, Uri.parse("https://www.renshuu.org/me/"))
        //startActivity(browserIntent)
    }
}

internal val widgetKey = stringPreferencesKey("widget-key")

data class Entry(
    val title: String,
    val toLearn: Int,
    val toReview: Int
)

fun refreshWidget(context: Context, glanceId: GlanceId) {
    MainScope().launch {
        val list = mutableListOf<Entry>()
        val prefs = context.getSharedPreferences("RWPrefs", Context.MODE_PRIVATE)
        prefs.getString("api_key", null)?.let {
            fetchUserData(context, it) { user ->
                if (user != null) {
                    user.
                    MainScope().launch {
                        updateAppWidgetState(
                            context = context,
                            definition = PreferencesGlanceStateDefinition,
                            glanceId = glanceId
                        ) { preferences ->
                            preferences.toMutablePreferences()
                                .apply {
                                    this[widgetKey] = Gson().toJson(list)
                                }
                        }
                        WidgetSmallRow().update(context, glanceId)
                    }
                }
            }
        }
    }
}