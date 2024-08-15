package com.finconsgroup.midgard.renshuuwidget

data class User(
    val id: Int,
    val real_name: String,
    val adventure_level: Int,
    val user_length: String,
    val kao: String,
    val studied: Studied,
    val level_progress_percs: LevelProgressPercs,
    val streaks: Streaks
)

data class Studied(
    val today_all: Int,
    val today_vocab: Int,
    val today_grammar: Int,
    val today_kanji: Int,
    val today_sent: Int,
    val today_conj: Int,
    val today_aconj: Int
)

data class LevelProgressPercs(
    val vocab: LevelProgress,
    val kanji: LevelProgress,
    val grammar: LevelProgress,
    val sent: LevelProgress
)

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

data class Streaks(
    val vocab: Streak,
    val kanji: Streak,
    val grammar: Streak,
    val sent: Streak,
    val conj: Streak,
    val aconj: Streak
)

data class Streak(
    val correct_in_a_row: Int,
    val correct_in_a_row_alltime: Int,
    val days_studied_in_a_row: Int,
    val days_studied_in_a_row_alltime: Int
)

data class ScheduleData(
    val schedules: List<Schedule>,
    val api_usage: ApiUsage
)

data class Schedule(
    val id: String,
    val name: String,
    val is_frozen: Int,
    val today: Today,
    val upcoming: List<Upcoming>,
    val terms: Terms,
    val new_terms: NewTerms
)

data class Today(
    val review: Int,
    val new: Int
)

data class Upcoming(
    val days_in_future: String,
    val terms_to_review: String
)

data class Terms(
    val total_count: Int,
    val studied_count: Int,
    val unstudied_count: Int,
    val hidden_count: Int
)

data class NewTerms(
    val today_count: Int,
    val rolling_week_count: Int
)

data class ApiUsage(
    val calls_today: Int,
    val daily_allowance: Int
)
