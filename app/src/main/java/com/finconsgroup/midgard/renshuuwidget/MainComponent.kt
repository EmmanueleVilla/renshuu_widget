package com.finconsgroup.midgard.renshuuwidget

import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import java.util.Date
import kotlin.random.Random

const val URL = "https://api.renshuu.org/v1/schedule"

fun fetchSchedule(context: Context, token: String, callback: (ScheduleData?) -> Unit) {
    val requestQueue: RequestQueue = Volley.newRequestQueue(context)

    val jsonObjectRequest = object : JsonObjectRequest(
        Method.GET, URL, null,
        Response.Listener { response ->
            Log.e("RESPONSE", response.toString())
            val gson = Gson()
            val data: ScheduleData = gson.fromJson(response.toString(), ScheduleData::class.java)
            callback(data)
        },
        Response.ErrorListener { error ->
            error.printStackTrace()
            callback(null)
        }
    ) {
        override fun getHeaders(): MutableMap<String, String> {
            val headers = HashMap<String, String>()
            headers["Authorization"] = "Bearer $token"
            Log.e("BEARER", "Bearer $token")
            return headers
        }
    }

    requestQueue.add(jsonObjectRequest)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MainComponent() {
    var loading by rememberSaveable {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    var text by rememberSaveable { mutableStateOf("") }
    var message by rememberSaveable { mutableStateOf("") }
    val schedulesData = rememberSaveable {
        mutableStateOf<ScheduleData?>(null)
    }
    val prefs = context.getSharedPreferences("RWPrefs", Context.MODE_PRIVATE)
    val key = rememberSaveable {
        mutableStateOf(
            prefs.getString("api_key", null)
        )
    }

    LaunchedEffect(key.value) {
        prefs.getString("api_key", null)?.let {
            fetchSchedule(context, it) { data ->
                schedulesData.value = data
                message = if (data != null) {
                    "Refresh ok ${Date()}"
                } else {
                    "Failed to fetch schedule data"
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(text = "RENSHUU WIDGET")
            })
        }
    ) {
        Column(
            Modifier
                .padding(it)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = "Set your api key",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = "API Key can be found under the experimental panel of renshuu's settings (tap menu, then the settings icon, then search for \"api\")"
            )
            val keyBeginning = key.value?.substring(0, 6) ?: ""
            val keyEnd = key.value?.substring(key.value?.length?.minus(6) ?: 0) ?: ""
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = "Current key: $keyBeginning...$keyEnd"
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                value = text,
                onValueChange = { text = it },
                label = { Text("API Key") },
                singleLine = true
            )
            Button(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                onClick = {
                    loading = true
                    prefs.edit().putString("api_key", text).apply()
                    key.value = text
                    text = ""
                    loading = false
                },
                enabled = !loading
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    text = "SAVE"
                )
            }
            AnimatedVisibility(visible = loading) {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
            AnimatedVisibility(visible = !loading) {
                Spacer(modifier = Modifier.size(64.dp))
            }
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = message
            )
            schedulesData.value?.let {
                it.schedules.forEach {
                    ListItem(
                        leadingContent = {
                            Text(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .drawBehind {
                                        drawCircle(
                                            color = when(it.name.first()) {
                                                'g', 'G' -> Color(0xff7acc18)
                                                'k', 'K' -> Color(0xffde8117)
                                                'w', 'W' -> Color(0xff5e5cd0)
                                                else -> Color.LightGray
                                            },
                                            radius = this.size.maxDimension
                                        )
                                    },
                                text = it.name.first().toString(),
                                color = Color.White
                            )
                        },
                        headlineContent = {
                            Text(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                text = it.name
                            )
                        },
                        supportingContent = {
                            Text(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                text = "To review: ${it.today.review}, to study: ${it.today.new}"
                            )
                        }
                    )

                }
            }
        }
    }
}