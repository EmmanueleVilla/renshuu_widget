package com.shadowings.renshuuwidget.models

data class LevelProgressPercs(
    val vocab: LevelProgress,
    val kanji: LevelProgress,
    val grammar: LevelProgress,
    val sent: LevelProgress
)