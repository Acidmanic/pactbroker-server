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
public class ColorProperty implements Property {

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

        return this.color.toCode();
    }

    @Override
    public void setValue(String value) {

        this.color = StyleColor.fromCode(value);
    }

    public StyleColor getColor() {
        return color;
    }

    public void setColor(StyleColor color) {
        this.color = color;
    }

    public ColorProperty(String name, StyleColor color) {
        this.name = name;
        this.color = color;
    }

    public ColorProperty() {
        this.name = "color";
        this.color = new StyleColor();
    }

}
