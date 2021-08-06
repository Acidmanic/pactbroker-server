/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.html.theme;

/**
 *
 * @author diego
 */
public class GenericProperty implements Property {

    private String name;
    private String value;

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
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    public GenericProperty(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public GenericProperty() {
    }

}
