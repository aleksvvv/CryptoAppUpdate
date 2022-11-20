package com.example.cryptoapp.utils

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun convertTimestampToTime(timestamp: Long?): String {
    if (timestamp == null) return ""
    val stamp = Timestamp(timestamp*1000)
    val date =Date(stamp.time)
    val pattern = "HH:mm:ss"
    //Locale.getDefault() текущее местоположение
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        //наша временная зона
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(date)
}