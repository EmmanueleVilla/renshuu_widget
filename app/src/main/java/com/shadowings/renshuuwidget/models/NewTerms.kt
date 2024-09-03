package com.shadowings.renshuuwidget.models

import com.google.gson.annotations.SerializedName

data class NewTerms(
    @SerializedName("today_count")
    val todayCount: Int,
    @SerializedName("rolling_week_count")
    val rollingWeekCount: Int
)
