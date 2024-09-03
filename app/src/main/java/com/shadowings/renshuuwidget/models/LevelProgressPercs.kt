package com.shadowings.renshuuwidget.models

import com.google.gson.annotations.SerializedName

data class LevelProgressPercs(
    @SerializedName("vocab")
    val vocab: LevelProgress,
    @SerializedName("kanji")
    val kanji: LevelProgress,
    @SerializedName("grammar")
    val grammar: LevelProgress,
    @SerializedName("sent")
    val sent: LevelProgress
)