/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.models;

import java.util.HashMap;

/**
 *
 * @author diego
 */
public class TemplateBaseMailRequest {

    private String from;
    private String[] recipients;
    private HashMap<String, String> substitutions = new HashMap<>();
    private String subject;
    private String templateName;

    public TemplateBaseMailRequest(String from, String[] recipients, String subject, String templateName) {
        this.from = from;
        this.recipients = recipients;
        this.subject = subject;
        this.templateName = templateName;
    }

    public TemplateBaseMailRequest() {
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

    public HashMap<String, String> getSubstitutions() {
        return substitutions;
    }

    public void setSubstitutions(HashMap<String, String> substitutions) {
        this.substitutions = substitutions;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

}
