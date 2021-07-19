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
public class SelfContainedMailRequest {

    private String from;
    private String[] recipients;
    private String base64Content;
    private boolean html;
    private String subject;

    public SelfContainedMailRequest(String from, String[] recipients, String base64Content, boolean html, String subject) {
        this.from = from;
        this.recipients = recipients;
        this.base64Content = base64Content;
        this.html = html;
        this.subject = subject;
    }

    public SelfContainedMailRequest() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String[] getRecipients() {
        return recipients;
    }

    public void setRecipients(String[] recipients) {
        this.recipients = recipients;
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
