package com.shadowings.renshuuwidget.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.shadowings.renshuuwidget.refresh.refreshWidget

class WidgetSmallRowReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = WidgetSmallRowComponent()

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        appWidgetIds.forEach {
            val glanceId =
                GlanceAppWidgetManager(context).getGlanceIdBy(it)
            refreshWidget(context, glanceId)
        }
    }
}
