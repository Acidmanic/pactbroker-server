/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.html;

import com.acidmanic.cicdassistant.utility.Result;

/**
 *
 * @author diego
 */
public class A extends ContainerHtmlTagBase {

    public String getHref() {

        Result<String> attribute = this.containsAttributeKey("href");

        if (attribute.isSuccessfull()) {

            return this.getAttribute(attribute.getValue());
        }
        return "";
    }

    public void setHref(String href) {

        this.setAttribute("href", href);
    }

    public String getTitle() {

        return this.getAttribute("title");
    }

    public void setTitle(String title) {

        this.setAttribute("title", title);
    }

    public A(String text, String href) {
        setHref(href);

        this.addChild(new RawString(text));
    }

    public A(String text, String href, String title) {

        setHref(href);

        setTitle(title);

        this.addChild(new RawString(text));
    }

    public A() {
    }

}
