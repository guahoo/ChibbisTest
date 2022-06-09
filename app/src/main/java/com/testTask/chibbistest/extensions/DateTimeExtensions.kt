package com.testTask.chibbistest.extensions

import java.text.SimpleDateFormat
import java.util.*

fun formatToDateToString(dateStr: String): String {
    val utc = TimeZone.getDefault()
    val sourceFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
    val destFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    destFormat.timeZone = utc
    val convertedDate = sourceFormat.parse(dateStr)
    return destFormat.format(convertedDate)
}