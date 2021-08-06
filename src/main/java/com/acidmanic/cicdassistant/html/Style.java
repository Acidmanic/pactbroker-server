/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.html;

import com.acidmanic.cicdassistant.html.styles.StyleColor;
import com.acidmanic.cicdassistant.html.theme.ColorProperty;
import com.acidmanic.cicdassistant.html.theme.GenericProperty;
import com.acidmanic.cicdassistant.html.theme.Property;
import java.util.HashMap;

/**
 *
 * @author diego
 */
public class Style implements Tag {

    private final HashMap<String, Property> properties = new HashMap<>();

    private String selector;

    public Style(String selector) {
        this.selector = selector;
    }

    public Style addProperty(String name, String value) {

        this.properties.put(name, new GenericProperty(name, value));

        return this;
    }

    public Style addProperty(Property property) {

        this.properties.put(property.getName(), property);

        return this;
    }

    public Style addColorProperty(String name, StyleColor color) {

        this.properties.put(name, new ColorProperty(name, color));

        return this;
    }

    public Style addColorProperty(String name, String code) {

        StyleColor color = StyleColor.fromCode(code);

        this.properties.put(name, new ColorProperty(name, color));

        return this;
    }

    @Override
    public StringBuilder append(StringBuilder sb) {

        sb.append(this.selector).append("{");

        this.properties.values().forEach(property -> append(sb, property));

        sb.append("}");

        return sb;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        this.append(sb);

        return sb.toString();
    }

    private void append(StringBuilder sb, Property property) {
        sb.append(property.getName()).append(":")
                .append(" ").append(property.getValue()).append("; ");
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

}
