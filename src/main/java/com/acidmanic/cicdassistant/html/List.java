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
public abstract class List extends ContainerHtmlTagBase {

    @Override
    public ContainerHtmlTagBase addChild(Tag child) {

        if (child instanceof Li) {

            return super.addChild(child);
        } else {

            return super.addChild(new Li().addChild(child));
        }

    }

}
