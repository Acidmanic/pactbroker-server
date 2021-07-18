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
public class RawString implements Tag {

    private String innerText;

    @Override
    public StringBuilder append(StringBuilder sb) {

        return sb.append(innerText);
    }

    public RawString() {
    }

    public RawString(String innerText) {
        this.innerText = innerText;
    }

    public String getInnerText() {
        return innerText;
    }

    public void setInnerText(String innerText) {
        this.innerText = innerText;
    }

}
