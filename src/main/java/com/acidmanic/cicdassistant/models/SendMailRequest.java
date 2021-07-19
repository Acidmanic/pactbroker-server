/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.models;

/**
 *
 * @author diego
 */
public class SendMailRequest {

    private String from;
    private String to;
    private String base64Content;
    private boolean html;
    private String subject;

    public SendMailRequest(String from, String to, String base64Content, boolean html, String subject) {
        this.from = from;
        this.to = to;
        this.base64Content = base64Content;
        this.html = html;
        this.subject = subject;
    }

    public SendMailRequest() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBase64Content() {
        return base64Content;
    }

    public void setBase64Content(String base64Content) {
        this.base64Content = base64Content;
    }

    public boolean isHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
