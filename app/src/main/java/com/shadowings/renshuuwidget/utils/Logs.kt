package com.shadowings.renshuuwidget.utils

import android.util.Log
import com.shadowings.renshuuwidget.BuildConfig

fun logIfDebug(message: String) {
    if (BuildConfig.DEBUG) {
        Log.d("VECNA", message)
    }
}
