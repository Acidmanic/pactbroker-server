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
public class WikiConfigurations {

    private String remote = "";
    private String username = "";
    private String password = "";
    private String wikiBranch = "";

    private int autoRefetchMinutes = 10;

    public String getRemote() {
        return remote;
    }

    public void setRemote(String remote) {
        this.remote = remote;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAutoRefetchMinutes() {
        return autoRefetchMinutes;
    }

    public void setAutoRefetchMinutes(int autoRefetchMinutes) {
        this.autoRefetchMinutes = autoRefetchMinutes;
    }

    public WikiConfigurations() {
    }

    public String getWikiBranch() {
        return wikiBranch;
    }

    public void setWikiBranch(String wikiBranch) {
        this.wikiBranch = wikiBranch;
    }
}
