package com.finconsgroup.midgard.renshuuwidget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.FontStyle
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.google.gson.Gson

/**
 * Single row widget
 */
class WidgetSmallRow : GlanceAppWidget() {
    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Content()
        }
    }

    @Composable
    fun Content() {
        val prefs = currentState<Preferences>()
        val json = prefs[widgetKey] ?: ""
        Report(json)
    }

    @Composable
    fun Report(json: String) {
        val deserialized: List<Entry> = Gson().fromJson(json, Array<Entry>::class.java).toList()
        val firstTitle = deserialized[0].title
        val firstToLearn = deserialized[0].toLearn
        val firstToReview = deserialized[0].toReview
        val totLearn = deserialized.sumOf { it.toLearn }
        val totReview = deserialized.sumOf { it.toReview }
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .clickable(onClick = actionStartActivity(MainActivity::class.java))
                .background(Color.Gray),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "$firstTitle", style = TextStyle(fontWeight = FontWeight.Bold))
            Text(
                text = "Learn: $firstToLearn - Review: $firstToReview",
                style = TextStyle(fontStyle = FontStyle.Italic)
            )
            Text(text = "Remaining", style = TextStyle(fontWeight = FontWeight.Bold))
            Text(
                text = "Learn: $totLearn - Review: $totReview",
                style = TextStyle(fontStyle = FontStyle.Italic)
            )
        }
    }
}