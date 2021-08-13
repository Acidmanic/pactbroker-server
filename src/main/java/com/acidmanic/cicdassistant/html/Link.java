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
public class Link extends HtmlTagBase {

    @Override
    protected boolean isSingleTag() {
        return true;
    }

    public Link() {

        this.setAttribute("crossorigin", "anonymous");
    }

    @Override
    protected StringBuilder appendContent(StringBuilder sb) {
        return sb;
    }

    public String getHref() {

        return this.getAttribute("href");
    }

    public void setHref(String href) {

        this.setAttribute("href", href);
    }

    public String getRel() {

        return this.getAttribute("rel");
    }

    public void setRel(String rel) {

        this.setAttribute("rel", rel);
    }

    public String getIntegrity() {

        return getAttribute("integrity");
    }

    public void setIntegrity(String integrity) {

        this.setAttribute("integrity", integrity);
    }

}
