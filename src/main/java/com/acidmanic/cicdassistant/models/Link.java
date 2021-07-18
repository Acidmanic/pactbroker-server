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
public class Link {

    private String title;
    private String name;
    private String href;
    private boolean templated;

    public Link() {
    }

    public Link(String title, String name, String href) {
        this.title = title;
        this.name = name;
        this.href = href;
        this.templated = false;
    }

    public Link(String title, String href, boolean templated) {
        this.title = title;
        this.href = href;
        this.templated = templated;
    }

    public boolean isTemplated() {
        return templated;
    }

    public void setTemplated(boolean templated) {
        this.templated = templated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}
