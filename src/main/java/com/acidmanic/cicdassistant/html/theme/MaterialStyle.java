/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.html.theme;

import com.acidmanic.cicdassistant.html.Style;
import com.acidmanic.cicdassistant.html.styles.StyleColor;

/**
 *
 * @author diego
 */
public class MaterialStyle extends Style {

    private final MaterialPalette palette;

    public MaterialStyle(MaterialPalette palette, String selector) {
        super(selector);
        this.palette = palette;
    }

    public MaterialStyle addColorProperty(String name, MaterialPaletteColors colorName) {

        StyleColor color = this.palette.get(colorName);

        super.addColorProperty(name, color);

        return this;
    }
}
