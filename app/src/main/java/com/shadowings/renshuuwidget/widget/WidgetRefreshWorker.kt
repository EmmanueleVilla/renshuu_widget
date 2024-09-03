package com.shadowings.renshuuwidget.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class WidgetRefreshWorker(private val ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        MainScope().launch {
            GlanceAppWidgetManager(ctx).getGlanceIds(WidgetSmallRow::class.java)
                .forEach { id ->
                    refreshWidget(ctx, id)
                }
        }
        return Result.success()
    }
}