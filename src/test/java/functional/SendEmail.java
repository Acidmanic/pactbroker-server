package functional;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

    public static void main(String[] args) {
        // Add recipient
        String to = "info@localhost";

        // Add sender
        String from = "shambalghuti@acidmanic.com";
//        final String username = "myusername@gmail.com";//your Gmail username 
//        final String password = "mypassword";//your Gmail password

        String host = "127.0.0.1";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "false");
        //props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        //props.put("mail.smtp.port", "587");

//        // Get the Session object
//        Session session = Session.getInstance(props,
//                new javax.mail.Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username, password);
//            }
//        });

        // Get the Session object
        Session session = Session.getInstance(props,new Authenticator() {}  );

        try {
            // Create a default MimeMessage object
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject
            message.setSubject("Hi JAXenter");

            // Put the content of your message
            //message.setText();
            
            message.setContent("Hi there,we are just experimenting with JavaMail here", "application/text");

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
