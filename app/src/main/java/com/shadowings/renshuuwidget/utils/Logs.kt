package com.shadowings.renshuuwidget.utils

import android.util.Log
import com.android.volley.BuildConfig

fun logIfDebug(message: String) {
    if (BuildConfig.DEBUG) {
        Log.d("RenshuuWidget", message)
    }
}