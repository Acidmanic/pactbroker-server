/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.application.configurations;

import com.acidmanic.cicdassistant.repositoryinfo.gitlab.GitlabConfiguration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author diego
 */
public class Configurations {

    private String mailSmtpServer;
    private HashMap<String, String> credentials = new HashMap<>();
    private List<GitlabConfiguration> gitlabConfigurations = new ArrayList<>();
    private int servicePort;
    private WikiConfigurations wikiConfigurations = new WikiConfigurations();
    private List<SshSession> sshSessions = new ArrayList<>();
    private WikiAuthorizationConfigurations authorizationConfigurations = new WikiAuthorizationConfigurations();

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

    public List<GitlabConfiguration> getGitlabConfigurations() {
        return gitlabConfigurations;
    }

    public void setGitlabConfigurations(List<GitlabConfiguration> gitlabConfigurations) {
        this.gitlabConfigurations = gitlabConfigurations;
    }

    public WikiConfigurations getWikiConfigurations() {
        return wikiConfigurations;
    }

    public void setWikiConfigurations(WikiConfigurations wikiConfigurations) {
        this.wikiConfigurations = wikiConfigurations;
    }

    public List<SshSession> getSshSessions() {
        return sshSessions;
    }

    public void setSshSessions(List<SshSession> sshSessions) {
        this.sshSessions = sshSessions;
    }

    public WikiAuthorizationConfigurations getAuthorizationConfigurations() {
        return authorizationConfigurations;
    }

    public void setAuthorizationConfigurations(WikiAuthorizationConfigurations authorizationConfigurations) {
        this.authorizationConfigurations = authorizationConfigurations;
    }

}
