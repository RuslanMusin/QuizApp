package com.example.quiz.presentation.util

import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter

class BarChartFormatter : ValueFormatter() {

    override fun getPieLabel(value: Float, pieEntry: PieEntry?): String {
        return if (value == 0f) "" else value.toInt().toString()
    }

}