/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.stylesheet.palettes;

import com.acidmanic.cicdassistant.html.styles.StyleColor;
import com.acidmanic.cicdassistant.html.theme.MaterialPaletteColors;

/**
 *
 * @author diego
 */
public class NamedMaterialPaletteBuilder {

    private final NamedMaterialPalette palette;

    public NamedMaterialPaletteBuilder(String name, MaterialPaletteColors flagColor) {

        this.palette = new NamedMaterialPalette(name, flagColor);
    }

    public NamedMaterialPaletteBuilder darkPrimary(String code) {

        this.palette.setDarkPrimaryColor(StyleColor.fromCode(code));

        return this;
    }

    public NamedMaterialPaletteBuilder accent(String code) {

        this.palette.setAccentColor(StyleColor.fromCode(code));

        return this;
    }

    public NamedMaterialPaletteBuilder lightPrimary(String code) {

        this.palette.setLightPrimaryColor(StyleColor.fromCode(code));

        return this;
    }

    public NamedMaterialPaletteBuilder primaryText(String code) {

        this.palette.setPrimaryText(StyleColor.fromCode(code));

        return this;
    }

    public NamedMaterialPaletteBuilder textIcons(String code) {

        this.palette.setTextIcons(StyleColor.fromCode(code));

        return this;
    }

    public NamedMaterialPaletteBuilder divider(String code) {

        this.palette.setDividerColor(StyleColor.fromCode(code));

        return this;
    }

    public NamedMaterialPaletteBuilder primary(String code) {

        this.palette.setPrimaryColor(StyleColor.fromCode(code));

        return this;
    }

    public NamedMaterialPalette build() {

        return this.palette;
    }
}
