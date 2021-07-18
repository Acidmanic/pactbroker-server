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
public class StyleSheet extends ContainerHtmlTagBase {

    @Override
    protected String tagName() {
        return "style";
    }

    @Override
    public ContainerHtmlTagBase addChild(Tag child) {

        if (child instanceof Style) {

            return super.addChild(child);
        }
        return this;
    }

}
