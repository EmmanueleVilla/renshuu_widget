package com.shadowings.renshuuwidget.models

import com.google.gson.annotations.SerializedName

data class Studied(
    @SerializedName("today_all")
    val todayAll: Int,
    @SerializedName("today_vocab")
    val todayVocab: Int,
    @SerializedName("today_grammar")
    val todayGrammar: Int,
    @SerializedName("today_kanji")
    val todayKanji: Int,
    @SerializedName("today_sent")
    val todaySent: Int,
    @SerializedName("today_conj")
    val todayConj: Int,
    @SerializedName("today_aconj")
    val todayAconj: Int
)