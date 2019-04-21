package com.example.quiz.presentation.util

import java.text.SimpleDateFormat
import java.util.*

fun String.parseToLocalDate(): String {
    val  sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale("ru"))
    val date = sdf.parse(this)

    val localSdg = SimpleDateFormat("dd.MM.yyy HH:mm:ss", Locale("ru"))

    return localSdg.format(date)
}