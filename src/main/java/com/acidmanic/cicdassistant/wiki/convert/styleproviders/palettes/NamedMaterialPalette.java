/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.styleproviders.palettes;

import com.acidmanic.cicdassistant.html.theme.MaterialPalette;
import com.acidmanic.cicdassistant.html.theme.MaterialPaletteColors;

/**
 *
 * @author diego
 */
public class NamedMaterialPalette extends MaterialPalette {

    private String name;
    private MaterialPaletteColors flagColor;

    public NamedMaterialPalette(String name, MaterialPaletteColors flagColor) {
        this.name = name;
        this.flagColor = flagColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MaterialPaletteColors getFlagColor() {
        return flagColor;
    }

    public void setFlagColor(MaterialPaletteColors flagColor) {
        this.flagColor = flagColor;
    }

    public String getFlagColorCode() {

        return this.get(this.flagColor).toCode();
    }

}
