package functional;

import com.acidmanic.cicdassistant.services.SmtpClient;

public class SendEmail {

    public static void main(String[] args) {
        
        SmtpClient client = new SmtpClient("mail.acidmanic.com")
                .authenticate("info@acidmanic.com", Passwords.InfoAtAcidmanicPassword);
        
        client.send("info@acidmanic.com", "goj_loj@yahoo.com",
                "\n This is test \n", "Text / plain");
        
    }
}
