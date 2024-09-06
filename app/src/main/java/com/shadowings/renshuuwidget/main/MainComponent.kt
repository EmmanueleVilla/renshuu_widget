package com.shadowings.renshuuwidget.main

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.shadowings.renshuuwidget.R
import com.shadowings.renshuuwidget.models.ScheduleData
import com.shadowings.renshuuwidget.ui.theme.GrammarColor
import com.shadowings.renshuuwidget.ui.theme.KanjiColor
import com.shadowings.renshuuwidget.ui.theme.RenshuuWidgetThemeComponent
import com.shadowings.renshuuwidget.ui.theme.VocabularyColor
import com.shadowings.renshuuwidget.utils.logIfDebug
import java.util.Date

const val URL = "https://api.renshuu.org/v1/schedule"

fun fetchSchedule(context: Context, token: String, callback: (ScheduleData?) -> Unit) {
    logIfDebug("fetchSchedule")
    val requestQueue: RequestQueue = Volley.newRequestQueue(context)

    val jsonObjectRequest = object : JsonObjectRequest(
        Method.GET, URL, null,
        Response.Listener { response ->
            logIfDebug("Response: $response")
            val gson = Gson()
            val data: ScheduleData = gson.fromJson(response.toString(), ScheduleData::class.java)
            callback(data)
        },
        Response.ErrorListener { error ->
            logIfDebug("Error: $error")
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

@Preview
@Composable
private fun MainComponentPreview() {
    RenshuuWidgetThemeComponent {
        MainComponent()
    }
}

@Composable
fun MainComponent() {
    val context = LocalContext.current
    val text = rememberSaveable { mutableStateOf("") }
    val message = rememberSaveable { mutableStateOf("") }
    val schedulesData = remember {
        mutableStateOf<ScheduleData?>(null)
    }
    val prefs = context.getSharedPreferences(stringResource(R.string.prefs_key), Context.MODE_PRIVATE)
    val key = rememberSaveable {
        mutableStateOf(
            prefs.getString("api_key", null)
        )
    }

    LaunchedEffect(key.value) {
        prefs.getString("api_key", null)?.let {
            fetchSchedule(context, it) { data ->
                schedulesData.value = data
                message.value = if (data != null) {
                    "Refresh ok ${Date()}"
                } else {
                    "Failed to fetch schedule data"
                }
            }
        }
    }

    MainComponentBody(key = key, text = text, message = message, schedulesData = schedulesData, prefs = prefs)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainComponentBody(
    key: MutableState<String?>,
    text: MutableState<String>,
    message: MutableState<String>,
    schedulesData: MutableState<ScheduleData?>,
    prefs: SharedPreferences,
) {
    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "RENSHUU WIDGET")
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MainComponentBodyContent(key, text, message, schedulesData, prefs)
        }
    }
}

private const val KEY_MASK_START = 0
private const val KEY_MASK_SIZE = 6

@Composable
private fun MainComponentBodyContent(
    key: MutableState<String?>,
    text: MutableState<String>,
    message: MutableState<String>,
    schedulesData: MutableState<ScheduleData?>,
    prefs: SharedPreferences,
) {
    val isLoading = rememberSaveable {
        mutableStateOf(false)
    }

    val keyBeginning = key.value?.substring(KEY_MASK_START, KEY_MASK_SIZE) ?: ""
    val keyEnd = key.value?.substring(key.value?.length?.minus(KEY_MASK_SIZE) ?: KEY_MASK_START) ?: ""

    Text(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        text = stringResource(R.string.setup),
        style = MaterialTheme.typography.headlineMedium
    )

    listOf(R.string.tutorial_1, R.string.tutorial_2, R.string.tutorial_3).forEach {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = stringResource(it)
        )
    }
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        value = text.value,
        onValueChange = { text.value = it },
        label = { Text(text = "API Key") },
        singleLine = true
    )
    Text(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        text = "Current key: $keyBeginning...$keyEnd"
    )
    SaveButton(isLoading = isLoading, key = key, text = text, prefs = prefs)

    Text(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        text = stringResource(R.string.blocked_tip)
    )

    AnimatedVisibility(visible = isLoading.value) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
    AnimatedVisibility(visible = !isLoading.value) {
        Spacer(modifier = Modifier.size(64.dp))
    }
    Text(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        text = message.value
    )
    ScheduleDataComponent(schedulesData)

    GithubButton()
}

@Composable
private fun GithubButton() {

    val context = LocalContext.current

    Button(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = {
            val intent = android.content.Intent(android.content.Intent.ACTION_VIEW)
            intent.data = android.net.Uri.parse("https://github.com/EmmanueleVilla/renshuu_widget")
            context.startActivity(intent)
        }
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = stringResource(R.string.git_contribute),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SaveButton(
    isLoading: MutableState<Boolean>,
    key: MutableState<String?>,
    prefs: SharedPreferences,
    text: MutableState<String>
) {
    Button(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = {
            isLoading.value = true
            prefs.edit().putString("api_key", text.value).apply()
            key.value = text.value
            text.value = ""
            isLoading.value = false
        },
        enabled = !isLoading.value
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = "SAVE"
        )
    }
}

@Composable
private fun ScheduleDataComponent(schedulesData: MutableState<ScheduleData?>) {
    schedulesData.value?.let {
        it.schedules.forEach {
            ListItem(
                leadingContent = {
                    Text(
                        modifier = Modifier
                            .padding(16.dp)
                            .drawBehind {
                                drawCircle(
                                    color = when (it.name.first()) {
                                        'g', 'G' -> GrammarColor
                                        'k', 'K' -> KanjiColor
                                        'w', 'W' -> VocabularyColor
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
