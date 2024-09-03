package com.shadowings.renshuuwidget.models

import com.google.gson.annotations.SerializedName

data class ApiUsage(
    @SerializedName("calls_today")
    val callsToday: Int,
    @SerializedName("daily_allowance")
    val dailyAllowance: Int
)
