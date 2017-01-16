package com.maxwittig.reportgenerator.utils

import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader


class FileUtils
{

    companion object
    {
        fun getFileContentFromJar(path: String): String?
        {
            val inputStream = FileUtils::class.java.getResourceAsStream(path)
            val bufferedInputStream = BufferedInputStream(inputStream)
            val bufferedReader = BufferedReader(InputStreamReader(bufferedInputStream))
            var line: String? = ""
            val text = StringBuilder()

            while (line != null)
            {
                line = bufferedReader.readLine()
                if (line != null)
                    text.append(line)
            }
            return text.toString()
        }
    }
}