/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.repositoryinfo.gitlab;

/**
 *
 * @author diego
 */
public class GitlabConfigurations {

    private String token;
    private String gitlabBaseUrl;
    private String projectId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGitlabBaseUrl() {
        return gitlabBaseUrl;
    }

    public void setGitlabBaseUrl(String gitlabBaseUrl) {
        this.gitlabBaseUrl = gitlabBaseUrl;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

}
