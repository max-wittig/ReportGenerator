package com.maxwittig.reportgenerator.tests.parser

import com.maxwittig.reportgenerator.parser.ConfigParser
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class ConfigParserTest
{
    @Test
    fun getSettingsTest()
    {
        val configString = "{" +
                "  \"fromAddress\":\"max@example.com\"," +
                "  \"toAddress\":\"max@example.com\"," +
                "  \"password\":\"Testing123\"," +
                "  \"smtpHost\": \"smtp.example.com\"," +
                "  \"port\":500," +
                "  \"dailyReport\": true," +
                "  \"weeklyReport\": true," +
                "  \"monthlyReport\": true," +
                "  \"yearlyReport\": true" +
                "}"

        val file = File.createTempFile("config",".json")
        file.deleteOnExit()
        file.writeText(configString)
        val parser = ConfigParser(file)
        val settings = parser.getSettings()
        assertEquals(settings.fromAddress, "max@example.com")
        assertEquals(settings.toAddress, "max@example.com")
        assertEquals(settings.password, "Testing123")
        assertEquals(settings.smtpHost, "smtp.example.com")
        assertEquals(settings.port, 500)
        assertEquals(settings.weeklyReportEnabled, true)
        assertEquals(settings.monthlyReportEnabled, true)
        assertEquals(settings.yearlyReportEnabled, true)
    }
}