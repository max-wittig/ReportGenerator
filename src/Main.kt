import java.io.File

fun main(args: Array<String>)
{
    //val is read-only
    val parser = ConfigParser(File(args[0]))
    val mailSettings = parser.getMailSettings()
    val reporter = MailSender(mailSettings)
    reporter.sendMail(mailSettings.toAddress, "Test")
}