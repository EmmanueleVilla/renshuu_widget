package com.shadowings.renshuuwidget.refresh

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.shadowings.renshuuwidget.utils.logIfDebug
import com.shadowings.renshuuwidget.widget.WidgetSmallRowComponent

class WidgetRefreshWorker(private val ctx: Context, params: WorkerParameters) :
    CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        logIfDebug("[WidgetRefreshWorker] refreshing widgets")
        GlanceAppWidgetManager(ctx).getGlanceIds(WidgetSmallRowComponent::class.java)
            .forEach { id ->
                logIfDebug("[WidgetRefreshWorker] refreshing widget: $id")
                refreshWidget(ctx, id)
            }
        return Result.success()
    }
}
