/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.html;

import java.util.HashMap;

/**
 *
 * @author diego
 */
public class Style implements Tag {

    private String selector;
    private final HashMap<String, String> properties;

    public Style() {

        this.properties = new HashMap<>();
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public HashMap<String, String> getProperties() {
        return properties;
    }

    @Override
    public StringBuilder append(StringBuilder sb) {

        sb.append(this.selector).append("{");

        for (String name : this.properties.keySet()) {

            String value = properties.get(name);

            sb.append(name).append(":").append(value).append(";");
        }
        sb.append("}");

        return sb;
    }

    @Override
    public String toString() {

        return append(new StringBuilder()).toString();
    }

    public void addProperty(String name, String value) {

        this.properties.put(name, value);
    }
}
