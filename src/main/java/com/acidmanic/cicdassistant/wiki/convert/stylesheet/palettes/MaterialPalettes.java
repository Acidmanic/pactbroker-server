/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.stylesheet.palettes;

import com.acidmanic.cicdassistant.html.theme.MaterialPaletteColors;

/**
 *
 * @author diego
 */
public class MaterialPalettes {

    public static final NamedMaterialPalette DARK_BLUE
            = new NamedMaterialPaletteBuilder("Dark Blue", MaterialPaletteColors.accentColor)
                    .accent("").darkPrimary("").divider("").lightPrimary("")
                    .primaryText("").textIcons("").build();

    public static final NamedMaterialPalette DARK_GREEN
            = new NamedMaterialPaletteBuilder("Dark Green", MaterialPaletteColors.accentColor)
                    .accent("").darkPrimary("").divider("").lightPrimary("")
                    .primaryText("").textIcons("").build();

    public static final NamedMaterialPalette[] ALL
            = {DARK_BLUE, DARK_GREEN};
}
