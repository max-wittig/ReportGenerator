package com.maxwittig.reportgenerator.handler
import com.maxwittig.reportgenerator.models.MailSettings
import org.simplejavamail.email.Email
import org.simplejavamail.mailer.Mailer
import org.simplejavamail.mailer.config.ServerConfig
import org.simplejavamail.mailer.config.TransportStrategy
import javax.mail.Message

class MailHandler(val mailSettings : MailSettings)
{
    private fun getGeneralMailObject(toAddress: String) : Email
    {
        val eMail = Email()
        eMail.setFromAddress(mailSettings.fromName, mailSettings.fromAddress)
        eMail.subject = mailSettings.getSubject()
        eMail.addRecipient(toAddress, toAddress, Message.RecipientType.TO)
        return eMail
    }

    fun sendPlainMail(toAddress: String, content : String)
    {
        val eMail = getGeneralMailObject(toAddress)
        eMail.text = content
        send(eMail)
    }

    fun sendHTMLMail(toAddress: String, content : String)
    {
        val eMail = getGeneralMailObject(toAddress)
        eMail.textHTML = content
        send(eMail)
    }

    private fun send(eMail : Email)
    {
        Mailer(ServerConfig(mailSettings.smtpHost, mailSettings.port, mailSettings.fromAddress, mailSettings.password)
                , TransportStrategy.SMTP_TLS).sendMail(eMail)
    }

}