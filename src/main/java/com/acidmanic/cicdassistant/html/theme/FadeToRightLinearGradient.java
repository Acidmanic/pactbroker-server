/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.html.theme;

import com.acidmanic.cicdassistant.html.styles.StyleColor;

/**
 *
 * @author diego
 */
public class FadeToRightLinearGradient implements Property {

    private String name;
    private StyleColor color;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getValue() {
        return "linear-gradient(to right, " + this.color.toCode() + " , #00000000)";
    }

    @Override
    public void setValue(String value) {

    }

    public StyleColor getColor() {
        return color;
    }

    public void setColor(StyleColor color) {
        this.color = color;
    }

}
