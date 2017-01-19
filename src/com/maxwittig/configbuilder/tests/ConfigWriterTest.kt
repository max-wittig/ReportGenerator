package com.maxwittig.configbuilder.tests

import com.maxwittig.configbuilder.ConfigWriter
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class ConfigWriterTest
{
    @Test
    fun writeTest()
    {
        val stringBuilder = StringBuilder()

        stringBuilder.append("{")
        stringBuilder.append("\"fromAddress\":\"max@example.com\",")
        stringBuilder.append("\"toAddress\":\"max@example.com\",")
        stringBuilder.append("\"password\":\"Testing123\",")
        stringBuilder.append("\"smtpHost\":\"smtp.example.com\",")
        stringBuilder.append("\"port\":500,")
        stringBuilder.append("\"dailyReport\":true,")
        stringBuilder.append("\"weeklyReport\":true,")
        stringBuilder.append("\"monthlyReport\":true,")
        stringBuilder.append("\"yearlyReport\":true")
        stringBuilder.append("}")


        val file = File.createTempFile("config","json")
        file.deleteOnExit()
        val writer = ConfigWriter(file)
        writer.write("max@example.com","max@example.com","Testing123","smtp.example.com", 500, true, true, true, true)
        assertEquals(stringBuilder.toString(), file.readText())
    }
}