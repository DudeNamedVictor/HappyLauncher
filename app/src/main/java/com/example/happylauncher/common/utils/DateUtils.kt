package com.example.happylauncher.common.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object DateUtils {
    private const val DATE_FORMATTER = "EEE, d MMM"
    private const val DATE_REPLACE_VALUE = "."

    fun getDate(): String {
        val dateFormat = SimpleDateFormat(DATE_FORMATTER, Locale.getDefault())
        val date = dateFormat.format(Date()).replace(DATE_REPLACE_VALUE, "")

        return date
    }

}