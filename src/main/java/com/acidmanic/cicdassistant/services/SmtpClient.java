/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.services;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author diego
 */
public class SmtpClient {

    private final String server;
    private int port = 25;
    private boolean useSsl = false;
    private String username = null;
    private String password = null;
    private boolean authenticate = false;

    public SmtpClient(String server) {
        this.server = server;
    }

    public SmtpClient port(int port) {
        this.port = port;
        return this;
    }

    public SmtpClient useSsl() {
        this.useSsl = true;
        return this;
    }

    public SmtpClient authenticate(String username, String password) {
        this.username = username;
        this.password = password;
        this.authenticate = true;
        return this;
    }

    public boolean sendTextMail(String from, String to, String body, String subject) {

        return this.send(from, to, body, subject, "text/plain");
    }
    
    public boolean sendHtmlMail(String from, String to, String body, String subject) {

        return this.send(from, to, body, subject, "text/html");
    }

    private boolean send(String from, String to, String body, String subject, String mime) {

        Session session = getSession();

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            message.setSubject(subject);

            message.setContent(body, mime);

            Transport.send(message);
                    
            return true;

        } catch (Exception e) {
            
            return false;
        }
    }

    private Properties getProperties() {

        Properties props = new Properties();
        props.put("mail.smtp.auth", this.authenticate ? "true" : "false");
        props.put("mail.smtp.starttls.enable", this.useSsl ? "true" : "false");
        props.put("mail.smtp.host", this.server);
        props.put("mail.smtp.port", this.port);

        return props;
    }

    private Session getSession() {

        Authenticator auth = this.authenticate
                ? new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        } : new Authenticator() {
        };

        Properties props = getProperties();
        // Get the Session object
        Session session = Session.getInstance(props, auth);

        return session;
    }

}
