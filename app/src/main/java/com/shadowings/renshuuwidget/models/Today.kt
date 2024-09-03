package com.shadowings.renshuuwidget.models

import com.google.gson.annotations.SerializedName

data class Today(
    @SerializedName("review")
    val review: Int,
    @SerializedName("new")
    val new: Int
)
