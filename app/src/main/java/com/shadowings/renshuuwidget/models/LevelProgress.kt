package com.shadowings.renshuuwidget.models

data class LevelProgress(
    val n1: Int,
    val n2: Int,
    val n3: Int,
    val n4: Int,
    val n5: Int,
    val n6: Int? = null,
    val kana: Int? = null,
    val kata: Int? = null
)