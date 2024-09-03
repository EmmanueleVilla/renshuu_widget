package com.shadowings.renshuuwidget.models

import com.google.gson.annotations.SerializedName

data class Upcoming(
    @SerializedName("days_in_future")
    val daysInFuture: String,
    @SerializedName("terms_to_review")
    val termsToReview: String
)