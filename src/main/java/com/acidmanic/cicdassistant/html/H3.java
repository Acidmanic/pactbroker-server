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
public class H3 extends ContainerHtmlTagBase {

    public H3() {
    }

    public H3(String textContent) {

        this.addChild(new RawString(textContent));
    }

}
