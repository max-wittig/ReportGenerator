import java.text.SimpleDateFormat
import java.util.*
import javax.jws.Oneway

class MailSettings(smtpHost : String, port : Int, fromAddress : String, password : String, fromName : String, toAddress : String)
{
    var fromAddress : String = fromAddress
        get
    var fromName : String = fromName
        get
    var smtpHost : String = smtpHost
        get
    var port : Int = port
        get
    var password : String = password
        get
    var toAddress : String = toAddress
        get

    fun getSubject() : String = "Daily Report from " +  SimpleDateFormat("dd.MM.YYYY").format(Date())

}