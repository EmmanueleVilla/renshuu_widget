package com.shadowings.renshuuwidget.models

import com.google.gson.annotations.SerializedName

data class LevelProgress(
    @SerializedName("n1")
    val n1: Int,
    @SerializedName("n2")
    val n2: Int,
    @SerializedName("n3")
    val n3: Int,
    @SerializedName("n4")
    val n4: Int,
    @SerializedName("n5")
    val n5: Int,
    @SerializedName("n6")
    val n6: Int? = null,
    @SerializedName("kana")
    val kana: Int? = null,
    @SerializedName("kata")
    val kata: Int? = null
)