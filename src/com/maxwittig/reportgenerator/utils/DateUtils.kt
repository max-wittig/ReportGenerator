package com.maxwittig.reportgenerator.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.*

fun isSameDay(date1 : Date, date2 : Date) : Boolean
{
    val simpleFormat = SimpleDateFormat("dd.MM.yyyy")
    return simpleFormat.format(date1) == simpleFormat.format(date2)
}

fun isSameWeek(date1 : Date, date2 : Date) : Boolean
{
    val simpleFormat = SimpleDateFormat("w.yyyy")
    return simpleFormat.format(date1) == simpleFormat.format(date2)
}

fun isSameMonth(date1 : Date, date2 : Date) : Boolean
{
    val simpleFormat = SimpleDateFormat("MM.yyyy")
    return simpleFormat.format(date1) == simpleFormat.format(date2)
}

fun isLastDayOfTheMonth() : Boolean
{
    val now = LocalDate.now()
    val lastDay = now.with(TemporalAdjusters.lastDayOfMonth())
    return now == lastDay
}

fun isLastDayOfTheWeek() : Boolean
{
    val now = LocalDate.now()
    return now.dayOfWeek.value == 7
}

fun getTimeStringFromSeconds(totalTime : Long) : String
{
    return LocalTime.ofSecondOfDay(totalTime).format(DateTimeFormatter.ISO_TIME).toString()
}
