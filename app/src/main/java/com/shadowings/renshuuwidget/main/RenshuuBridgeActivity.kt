package com.shadowings.renshuuwidget.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.shadowings.renshuuwidget.refresh.WidgetRefreshWorker
import com.shadowings.renshuuwidget.refresh.refreshWidget
import com.shadowings.renshuuwidget.widget.WidgetSmallRowComponent
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class RenshuuBridgeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedDispatcher.addCallback {
            startActivity(Intent(this@RenshuuBridgeActivity, MainActivity::class.java))
            finish()
        }

        val workManager = WorkManager.getInstance(applicationContext)

        workManager.enqueueUniquePeriodicWork(
            "widget refresh",
            ExistingPeriodicWorkPolicy.UPDATE,
            PeriodicWorkRequest
                .Builder(WidgetRefreshWorker::class.java, REPEAT_TIME, TimeUnit.MINUTES)
                .build()
        )

        MainScope().launch {
            GlanceAppWidgetManager(this@RenshuuBridgeActivity).getGlanceIds(WidgetSmallRowComponent::class.java)
                .forEach { id ->
                    refreshWidget(this@RenshuuBridgeActivity, id)
                }
        }

        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.renshuu.org/me/"))
        startActivity(browserIntent)
    }

    override fun onResume() {
        super.onResume()
        finish()
    }
}
