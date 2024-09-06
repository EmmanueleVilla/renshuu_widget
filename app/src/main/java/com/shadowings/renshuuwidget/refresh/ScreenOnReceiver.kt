package com.shadowings.renshuuwidget.refresh

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.shadowings.renshuuwidget.utils.logIfDebug
import kotlin.math.log


class ScreenOnReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        logIfDebug("[ScreenOnReceiver] received intent: ${intent.action}")
        if (Intent.ACTION_SCREEN_ON == intent.action) {
            if (shouldRunWork(context)) {
                scheduleWork(context)
            }
        }
    }

    /**
     * Returns true if the last work request was more than 15 minutes ago.
     */
    private fun shouldRunWork(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val lastRunTime = prefs.getLong(LAST_RUN_TIME, 0)
        val currentTime = System.currentTimeMillis()
        val shouldRun = (currentTime - lastRunTime) > FIFTEEN_MINUTES
        logIfDebug("[ScreenOnReceiver] shouldRunWork: $shouldRun")
        return shouldRun
    }

    /**
     * Schedules a work request to refresh the widget.
     */
    private fun scheduleWork(context: Context) {
        logIfDebug("[ScreenOnReceiver] scheduling work")
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putLong(LAST_RUN_TIME, System.currentTimeMillis())
        editor.apply()

        val workRequest = OneTimeWorkRequest.Builder(WidgetRefreshWorker::class.java)
            .build()
        WorkManager.getInstance(context).enqueue(workRequest)
    }

    companion object {
        private const val PREFS_NAME = "WorkManagerPrefs"
        private const val LAST_RUN_TIME = "last_run_time"
        private const val FIFTEEN_MINUTES = (15 * 60 * 1000).toLong()
    }
}
