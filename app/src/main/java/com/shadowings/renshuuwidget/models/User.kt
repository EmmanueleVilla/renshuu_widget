package com.shadowings.renshuuwidget.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int,
    @SerializedName("real_name")
    val realName: String,
    @SerializedName("adventure_level")
    val adventureLevel: Int,
    @SerializedName("user_length")
    val userLength: String,
    @SerializedName("kao")
    val kao: String,
    @SerializedName("studied")
    val studied: Studied,
    @SerializedName("level_progress_percs")
    val levelProgressPercs: LevelProgressPercs,
    @SerializedName("streaks")
    val streaks: Streaks
)
