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
    private HashMap<String, String> credentials = new HashMap<>();
    private int servicePort;

    public String getMailSmtpServer() {
        return mailSmtpServer;
    }

    public void setMailSmtpServer(String mailSmtpServer) {
        this.mailSmtpServer = mailSmtpServer;
    }

    public HashMap<String, String> getCredentials() {
        return credentials;
    }

    public void setCredentials(HashMap<String, String> credentials) {
        this.credentials = credentials;
    }

    public int getServicePort() {
        return servicePort;
    }

    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }

}
