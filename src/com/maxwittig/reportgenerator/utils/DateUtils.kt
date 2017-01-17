package com.maxwittig.reportgenerator.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import java.util.*
import java.util.concurrent.TimeUnit

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

fun isSameYear(date1 : Date, date2 : Date) : Boolean
{
    val simpleFormat = SimpleDateFormat("yyyy")
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

fun isLastDayOfTheYear() : Boolean
{
    val now = LocalDate.now()
    val lastDay = now.with(TemporalAdjusters.lastDayOfYear())
    return now == lastDay
}

fun getTimeStringFromMilliSeconds(totalTime : Long) : String
{
    val hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(totalTime),
            TimeUnit.MILLISECONDS.toMinutes(totalTime) % TimeUnit.HOURS.toMinutes(1),
            TimeUnit.MILLISECONDS.toSeconds(totalTime) % TimeUnit.MINUTES.toSeconds(1))
    return hms
}
