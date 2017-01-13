package com.maxwittig.reportgenerator.utils

import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

fun isSameDay(date1 : Date, date2 : Date) : Boolean
{
    val simpleFormat = SimpleDateFormat("dd.MM.yyyy")
    return simpleFormat.format(date1) == simpleFormat.format(date2)
}

fun getTimeStringFromSeconds(totalTime : Long) : String
{
    return LocalTime.ofSecondOfDay(totalTime).format(DateTimeFormatter.ISO_TIME).toString()
}