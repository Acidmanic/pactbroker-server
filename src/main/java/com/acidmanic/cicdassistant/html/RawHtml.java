/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.html;

/**
 * This tag, just reflects inner html. I will not render any start and end tags
 *
 * @author diego
 */
public class RawHtml implements Tag {

    private String innerHtml = "";

    public RawHtml() {
    }

    public RawHtml(String innerHtml) {
        this.innerHtml = innerHtml;
    }

    public String getInnerHtml() {
        return innerHtml;
    }

    public void setInnerHtml(String innerHtml) {
        this.innerHtml = innerHtml;
    }

    @Override
    public StringBuilder append(StringBuilder sb) {

        sb.append(this.innerHtml);

        return sb;
    }

}
