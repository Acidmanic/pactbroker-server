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
public class Div extends ContainerHtmlTagBase {

    public Div() {
    }

    public Div(String cssClasses) {

        this.setCssClass(cssClasses);
    }

    public Div(Tag... children) {

        for (Tag child : children) {

            this.addChild(child);
        }
    }

    public Div(String cssClasses, Tag... children) {

        this.setCssClass(cssClasses);

        for (Tag child : children) {

            this.addChild(child);
        }
    }

}
