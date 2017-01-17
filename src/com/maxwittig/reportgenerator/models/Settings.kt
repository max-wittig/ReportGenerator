package com.maxwittig.reportgenerator.models


class Settings(val smtpHost : String,
               val port : Int,
               val fromAddress : String,
               val password : String,
               val fromName : String,
               val toAddress : String,
               val weeklyReportEnabled : Boolean,
               val monthlyReportEnabled : Boolean,
               val yearlyReportEnabled : Boolean)