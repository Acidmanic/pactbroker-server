/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.autoindexing;

/**
 *
 * @author diego
 */
public class MarkdownLink {

    private String href;

    private String text;

    public MarkdownLink(String href, String text) {
        this.href = href;
        this.text = text;
    }

    public MarkdownLink() {
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
