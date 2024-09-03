package com.shadowings.renshuuwidget.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
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
import androidx.glance.text.TextStyle
import com.google.gson.Gson
import com.shadowings.renshuuwidget.main.RenshuuBridgeActivity
import com.shadowings.renshuuwidget.models.ScheduleData
import com.shadowings.renshuuwidget.main.widgetKey

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

    @Composable
    fun ContentComponent() {
        val prefs = currentState<Preferences>()
        val json = prefs[widgetKey] ?: ""
        ReportComponent(json)
    }

    @Composable
    fun ReportComponent(json: String?) {
        val deserialized = Gson().fromJson(json, ScheduleData::class.java)
        LazyColumn(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(Color.Gray),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(deserialized.schedules.size) {
                val value = deserialized.schedules[it]
                Column(
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .padding(2.dp)
                        .clickable(actionStartActivity(RenshuuBridgeActivity::class.java)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = value.name, style = TextStyle(fontWeight = FontWeight.Bold))
                    Text(
                        text = "Learn: ${value.today.new} - Review: ${value.today.review}",
                        style = TextStyle(fontStyle = FontStyle.Italic)
                    )
                    if (it < deserialized.schedules.size - 1) {
                        Row(
                            GlanceModifier.fillMaxWidth().padding(2.dp).height(1.dp)
                                .background(Color.DarkGray)
                        ) {}
                    }
                }
            }
        }
    }
}
