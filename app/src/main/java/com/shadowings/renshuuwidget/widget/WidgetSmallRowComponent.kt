package com.shadowings.renshuuwidget.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.FontStyle
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.google.gson.Gson
import com.shadowings.renshuuwidget.main.MainActivity
import com.shadowings.renshuuwidget.main.RenshuuBridgeActivity
import com.shadowings.renshuuwidget.main.themeKey
import com.shadowings.renshuuwidget.main.themes
import com.shadowings.renshuuwidget.main.widgetKey
import com.shadowings.renshuuwidget.models.Schedule
import com.shadowings.renshuuwidget.models.ScheduleData
import com.shadowings.renshuuwidget.utils.logIfDebug

/**
 * Single row widget
 */
class WidgetSmallRowComponent : GlanceAppWidget() {
    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            ContentComponent()
        }
    }
}

@Composable
fun ContentComponent() {
    val prefs = currentState<Preferences>()
    val json = prefs[widgetKey] ?: ""
    val theme = prefs[themeKey]?.toIntOrNull() ?: 0
    ReportComponent(json, theme)
}

@Composable
fun ReportComponent(json: String?, theme: Int) {
    logIfDebug("Received theme index: $theme")
    val deserialized = Gson().fromJson(json, ScheduleData::class.java)
    val themeIndex = theme % themes.size
    LazyColumn(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(themes[themeIndex].backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        deserialized.schedules?.let { schedules ->
            items(schedules.size) {
                val value = schedules[it]
                ScheduleItem(value, themeIndex)
            }
        }
        item {
            GoToSettings(themeIndex)
        }
    }
}

@Composable
fun ScheduleItem(value: Schedule, themeIndex: Int) {
    Column(
        modifier = GlanceModifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable(actionStartActivity(RenshuuBridgeActivity::class.java)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = value.name,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = ColorProvider(themes[themeIndex].textColor)
            )
        )
        Text(
            text = "Learn: ${value.today.new} - Review: ${value.today.review}",
            style = TextStyle(
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                color = ColorProvider(themes[themeIndex].textColor)
            ),
        )
        Row(
            GlanceModifier.fillMaxWidth().padding(2.dp).height(1.dp)
                .background(themes[themeIndex].textColor)
        ) {}
    }
}


@Composable
private fun GoToSettings(themeIndex: Int) {
    Column(
        modifier = GlanceModifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable(actionStartActivity(MainActivity::class.java)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Go to settings",
            style = TextStyle(
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                color = ColorProvider(themes[themeIndex].textColor)
            ),
        )
    }
}
