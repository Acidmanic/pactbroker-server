package functional;

import com.acidmanic.cicdassistant.html.Html;
import com.acidmanic.cicdassistant.services.SmtpClient;

public class SendEmail {

    public static void main(String[] args) {

        String password = TestDataProvider.get().getProperty("InfoAtAcidmanicPassword");

        SmtpClient client = new SmtpClient("mail.acidmanic.com")
                .authenticate("info@acidmanic.com", password);

        Html html = HtmlGenerating.createHtmlContent();

        client.sendHtmlMail("info@acidmanic.com", "goj_loj@yahoo.com",
                html.getBody().toString(), "html body email");

    }
}
