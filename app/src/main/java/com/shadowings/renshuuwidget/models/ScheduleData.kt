package com.shadowings.renshuuwidget.models

import com.google.gson.annotations.SerializedName

data class ScheduleData(
    @SerializedName("schedules")
    val schedules: List<Schedule>,
    @SerializedName("api_usage")
    val apiUsage: ApiUsage
)