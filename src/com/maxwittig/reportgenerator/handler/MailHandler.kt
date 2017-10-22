package com.maxwittig.reportgenerator.handler

import com.maxwittig.reportgenerator.models.Settings
import org.simplejavamail.email.Email
import org.simplejavamail.email.Recipient
import org.simplejavamail.mailer.Mailer
import org.simplejavamail.mailer.config.ServerConfig
import org.simplejavamail.mailer.config.TransportStrategy
import java.text.SimpleDateFormat
import java.util.*
import javax.mail.Message

class MailHandler(val settings: Settings) {
    private fun getGeneralMailObject(toAddress: String): Email {
        val eMail = Email()
        eMail.setFromAddress(settings.fromName, settings.fromAddress)
        eMail.subject = getSubject()
        eMail.addRecipients(Recipient(toAddress, toAddress, Message.RecipientType.TO))
        return eMail
    }

    fun sendPlainMail(toAddress: String, content: String) {
        val eMail = getGeneralMailObject(toAddress)
        eMail.text = content
        send(eMail)
    }

    fun sendHTMLMail(toAddress: String, content: String) {
        val eMail = getGeneralMailObject(toAddress)
        eMail.textHTML = content
        send(eMail)
    }

    private fun send(eMail: Email) {
        Mailer(ServerConfig(settings.smtpHost, settings.port, settings.fromAddress, settings.password)
                , TransportStrategy.SMTP_TLS).sendMail(eMail)
    }

    private fun getSubject(): String = "Report from " + SimpleDateFormat("dd.MM.YYYY").format(Date())

}