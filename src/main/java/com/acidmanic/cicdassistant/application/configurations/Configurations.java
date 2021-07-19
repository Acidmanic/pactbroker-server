/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.application.configurations;

import java.util.HashMap;

/**
 *
 * @author diego
 */
public class Configurations {
    
    
    private String mailSmtpServer;
    private HashMap<String,String> credencials = new HashMap<>();

    public String getMailSmtpServer() {
        return mailSmtpServer;
    }

    public void setMailSmtpServer(String mailSmtpServer) {
        this.mailSmtpServer = mailSmtpServer;
    }

    public HashMap<String, String> getCredencials() {
        return credencials;
    }

    public void setCredencials(HashMap<String, String> credencials) {
        this.credencials = credencials;
    }
    
}
