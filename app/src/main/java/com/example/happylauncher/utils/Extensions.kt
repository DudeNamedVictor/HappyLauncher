package com.example.happylauncher.utils

import android.app.Activity
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun getDate(): String {
    val dateFormat = SimpleDateFormat(Constants.DATE_FORMATTER, Locale.getDefault())
    val date = dateFormat.format(Date()).replace(Constants.DATE_REPLACE_VALUE, "")

    return date
}