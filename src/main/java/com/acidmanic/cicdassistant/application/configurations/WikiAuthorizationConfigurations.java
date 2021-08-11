/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.application.configurations;

/**
 *
 * @author diego
 */
public class WikiAuthorizationConfigurations {

    private String gitlabBaseUrl = null;
    private boolean useGitlab = false;

    public String getGitlabBaseUrl() {
        return gitlabBaseUrl;
    }

    public void setGitlabBaseUrl(String gitlabBaseUrl) {
        this.gitlabBaseUrl = gitlabBaseUrl;
    }

    public boolean isUseGitlab() {
        return useGitlab;
    }

    public void setUseGitlab(boolean useGitlab) {
        this.useGitlab = useGitlab;
    }

}
