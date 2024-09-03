package com.shadowings.renshuuwidget.models

import com.google.gson.annotations.SerializedName

data class Terms(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("studied_count")
    val studiedCount: Int,
    @SerializedName("unstudied_count")
    val unstudiedCount: Int,
    @SerializedName("hidden_count")
    val hiddenCount: Int
)
