package functional;

import com.acidmanic.cicdassistant.services.SmtpClient;

public class SendEmail {

    public static void main(String[] args) {
        
        SmtpClient client = new SmtpClient("127.0.0.1");
        
        client.send("diego@acidmanic.com", "info@localhost",
                "\n This is test \n", "Test email");
        
    }
}
