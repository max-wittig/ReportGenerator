package com.maxwittig.reportgenerator.tests.utils

import com.maxwittig.reportgenerator.utils.isSameDay
import com.maxwittig.reportgenerator.utils.isSameMonth
import com.maxwittig.reportgenerator.utils.isSameYear
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.text.SimpleDateFormat

class DateUtilsTest
{
    private val format = SimpleDateFormat("dd.MM.yyyy")

    @Test
    fun isSameYearTest()
    {
        val date1 = format.parse("17.01.2017")
        val date2 = format.parse("25.10.2017")
        assertTrue(isSameYear(date1, date2))
    }

    @Test
    fun isSameMonthTest()
    {
        val date1 = format.parse("17.10.2017")
        val date2 = format.parse("25.10.2017")
        val date3 = format.parse("25.07.2017")
        assertTrue(isSameMonth(date1, date2))
        assertFalse(isSameMonth(date1, date3))
    }

    @Test
    fun isSameDayTest()
    {
        val date1 = format.parse("17.10.2017")
        val date2 = format.parse("17.10.2017")
        val date3 = format.parse("25.07.2017")
        assertTrue(isSameDay(date1, date2))
        assertFalse(isSameDay(date1, date3))
    }
}