package com.maxwittig

import java.text.SimpleDateFormat
import java.util.*

fun isSameDay(date1 : Date, date2 : Date) : Boolean
{
    val simpleFormat = SimpleDateFormat("dd.MM.yyyy")
    return simpleFormat.format(date1) == simpleFormat.format(date2)
}