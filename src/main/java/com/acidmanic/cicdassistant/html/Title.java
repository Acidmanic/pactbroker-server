/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.html;

/**
 *
 * @author diego
 */
public class Title extends HtmlTagBase {

    private String title;

    @Override
    protected boolean isSingleTag() {
        return false;
    }

    @Override
    protected StringBuilder appendContent(StringBuilder sb) {

        return sb.append(this.title);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
