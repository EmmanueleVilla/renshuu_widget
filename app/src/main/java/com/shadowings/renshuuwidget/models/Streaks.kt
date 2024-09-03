package com.shadowings.renshuuwidget.models

import com.google.gson.annotations.SerializedName

data class Streaks(
    @SerializedName("vocab")
    val vocab: Streak,
    @SerializedName("kanji")
    val kanji: Streak,
    @SerializedName("grammar")
    val grammar: Streak,
    @SerializedName("sent")
    val sent: Streak,
    @SerializedName("conj")
    val conj: Streak,
    @SerializedName("aconj")
    val aconj: Streak
)
