/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.styleproviders.palettes;

import com.acidmanic.cicdassistant.html.theme.MaterialPaletteColors;

/**
 *
 * @author diego
 */
public class MaterialPalettes {

    public static final NamedMaterialPalette LIME_GREEN
            = new NamedMaterialPaletteBuilder("Lime Green", MaterialPaletteColors.accentColor)
                    .accent("#4CAF50").darkPrimary("#AFB42B").divider("#BDBDBD")
                    .lightPrimary("#F0F4C3").primaryText("#212121").primary("#CDDC39")
                    .textIcons("#212121").build();

    public static final NamedMaterialPalette DARK_GREEN
            = new NamedMaterialPaletteBuilder("Dark Green", MaterialPaletteColors.accentColor)
                    .accent("#607D8B").darkPrimary("#388E3C").divider("#BDBDBD")
                    .lightPrimary("#4CAF50").primaryText("#212121").primary("#4CAF50")
                    .textIcons("#FFFFFF").build();

    public static final NamedMaterialPalette DARK_BLUE
            = new NamedMaterialPaletteBuilder("Dark Blue", MaterialPaletteColors.accentColor)
                    .accent("#536DFE").darkPrimary("#455A64").divider("#BDBDBD")
                    .lightPrimary("#CFD8DC").primaryText("#212121").primary("#3F51B5")
                    .textIcons("#FFFFFF").build();

    public static final NamedMaterialPalette DARK_ORANGE
            = new NamedMaterialPaletteBuilder("Dark Orange", MaterialPaletteColors.accentColor)
                    .accent("#FF5722").darkPrimary("#616161").divider("#BDBDBD")
                    .lightPrimary("#F5F5F5").primaryText("#212121").primary("#9E9E9E")
                    .textIcons("#212121").build();

    public static final NamedMaterialPalette MEDIUM_GRAY
            = new NamedMaterialPaletteBuilder("Medium Gray", MaterialPaletteColors.accentColor)
                    .accent("#9E9E9E").darkPrimary("#455A64").divider("#BDBDBD")
                    .lightPrimary("#CFD8DC").primaryText("#212121").primary("#607D8B")
                    .textIcons("#FFFFFF").build();

    public static final NamedMaterialPalette[] ALL
            = {LIME_GREEN, DARK_GREEN, DARK_BLUE, DARK_ORANGE, MEDIUM_GRAY};
}
