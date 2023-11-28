package com.finconsgroup.midgard.renshuuwidget

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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

    private val workManager = WorkManager.getInstance(applicationContext)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RenshuuWidgetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Text(text = "Hello World!")
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

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
        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.renshuu.org/index.php?page=user/me"))
        startActivity(browserIntent)
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
        val queue: RequestQueue = Volley.newRequestQueue(context)

        val url = "https://www.renshuu.org/me/"

        val stringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener { response ->
                val list = mutableListOf<Entry>()
                val doc = org.jsoup.Jsoup.parse(response)
                doc.getElementById("dash_schedbox2")?.html()?.let { html ->
                    html.split("<div id=\"sched_box_").forEach {
                        try {
                            org.jsoup.Jsoup.parse(it).apply {
                                getElementsByClass("hsched_button").first()?.text()?.let { text ->
                                    val toLearn = it.split("</span> to learn").firstOrNull()
                                        ?.substringAfterLast(">")?.toIntOrNull() ?: 0
                                    val toReview = it.split("</span> to review").firstOrNull()
                                        ?.substringAfterLast(">")?.toIntOrNull() ?: 0
                                    list.add(
                                        Entry(
                                            title = text,
                                            toLearn = toLearn,
                                            toReview = toReview
                                        )
                                    )
                                }
                            }
                        } catch (e: Exception) {
                            println("Error: ${e.message}")
                        }
                    }
                }
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
            },
            Response.ErrorListener { error ->
                println("Errore: ${error.message}")
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Cookie"] = "PHPSESSID=vra9j01cljpjrvn11vm9bc117q"
                return headers
            }
        }

        queue.add(stringRequest)
    }
}