package com.shadowings.renshuuwidget.models

import com.google.gson.annotations.SerializedName

data class Schedule(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("is_frozen")
    val isFrozen: Int,
    @SerializedName("today")
    val today: Today,
    @SerializedName("upcoming")
    val upcoming: List<Upcoming>,
    @SerializedName("terms")
    val terms: Terms,
    @SerializedName("new_terms")
    val newTerms: NewTerms
)