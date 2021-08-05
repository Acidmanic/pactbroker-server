/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.anchorsources;

/**
 *
 * @author diego
 */
public class EncyclopediaEntry {

    private String description;
    private String title;
    private String webLink;

    public EncyclopediaEntry(String description, String title, String webLink) {
        this.description = description;
        this.title = title;
        this.webLink = webLink;
    }

    public EncyclopediaEntry(String webLink) {
        this.webLink = webLink;
        this.title = webLink;
        this.description = webLink;
    }

    public EncyclopediaEntry() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

}
