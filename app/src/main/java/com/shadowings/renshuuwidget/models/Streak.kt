package com.shadowings.renshuuwidget.models

import com.google.gson.annotations.SerializedName

data class Streak(
    @SerializedName("correct_in_a_row")
    val correctInARow: Int,
    @SerializedName("correct_in_a_row_alltime")
    val correctInARowAllTime: Int,
    @SerializedName("days_studied_in_a_row")
    val daysStudiedInARow: Int,
    @SerializedName("days_studied_in_a_row_alltime")
    val daysStudiedInARowAllTime: Int
)