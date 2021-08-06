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
public enum MaterialPaletteColors {
    darkPrimaryColor(0), accentColor(1), lightPrimaryColor(2), primaryText(3), primaryColor(4), secondaryText(5), textIcons(6), dividerColor(7), Count(8);

    private final int value;

    MaterialPaletteColors(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

}
