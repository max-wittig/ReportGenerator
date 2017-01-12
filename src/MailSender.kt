
import org.simplejavamail.email.Email
import org.simplejavamail.mailer.Mailer
import org.simplejavamail.mailer.config.ServerConfig
import org.simplejavamail.mailer.config.TransportStrategy
import javax.mail.Message

class MailSender(mailSettings: MailSettings)
{
    private var mailSettings = mailSettings

    fun sendMail(toAddress: String, content : String)
    {
        val eMail = Email()
        System.out.println(mailSettings.fromName)
        eMail.setFromAddress(mailSettings.fromName, mailSettings.fromAddress)
        eMail.subject = mailSettings.getSubject()
        eMail.addRecipient(toAddress, toAddress, Message.RecipientType.TO)
        eMail.text = content
        Mailer(ServerConfig(mailSettings.smtpHost, mailSettings.port, mailSettings.fromAddress, mailSettings.password)
                ,TransportStrategy.SMTP_TLS).sendMail(eMail)
    }
}