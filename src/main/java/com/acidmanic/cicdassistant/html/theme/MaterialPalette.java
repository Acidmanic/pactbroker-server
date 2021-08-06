/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.html.theme;

import com.acidmanic.cicdassistant.html.styles.StyleColor;
import com.acidmanic.cicdassistant.html.styles.StyleColorUtils;
import com.acidmanic.cicdassistant.utility.StringUtils;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author diego
 */
public class MaterialPalette {

    private StyleColor[] colors;

    public MaterialPalette() {

        this.colors = new StyleColor[MaterialPaletteColors.Count.value()];

    }

    public MaterialPalette(String paletteCodes) {
        this();

        List<String> segments = StringUtils.split(paletteCodes, ",", true);

        if (segments.size() == MaterialPaletteColors.Count.value()) {

            List<StyleColor> parsedColors = StyleColorUtils.parse(segments);

            for (int i = 0; i < MaterialPaletteColors.Count.value(); i++) {

                this.colors[i] = parsedColors.get(i);
            }
        }
    }

    public static MaterialPalette fromPolymer(String polymerProperties) {

        MaterialPalette palette = new MaterialPalette();

        palette.laodPolymer(polymerProperties);

        return palette;
    }

    public void laodPolymer(String polymerProperties) {

        List<String> lines = StringUtils.extractPatterns(polymerProperties, "\\-\\-[a-z\\-]+:\\s*#[0-9a-zA-z]+");

        HashMap<String, StyleColor> colors = new HashMap<>();

        for (String line : lines) {

            line = line.replaceAll("\\s", "");
            line = line.replaceAll(";", "");
            line = line.replaceAll("\\-", "");

            String[] segments = line.split(":");

            if (segments.length == 2) {

                StyleColor color = StyleColor.fromCode(segments[1]);

                colors.put(segments[0], color);
            }
        }

        StyleColor black = new StyleColor(0, 0, 0, 1);

        this.colors[MaterialPaletteColors.darkPrimaryColor.value()]
                = colors.getOrDefault("darkprimarycolor", black);
        this.colors[MaterialPaletteColors.accentColor.value()]
                = colors.getOrDefault("accentcolor", black);
        this.colors[MaterialPaletteColors.dividerColor.value()]
                = colors.getOrDefault("dividercolor", black);
        this.colors[MaterialPaletteColors.lightPrimaryColor.value()]
                = colors.getOrDefault("lightprimarycolor", black);
        this.colors[MaterialPaletteColors.primaryText.value()]
                = colors.getOrDefault("primarytextcolor", black);
        this.colors[MaterialPaletteColors.secondaryText.value()]
                = colors.getOrDefault("secondarytextcolor", black);
        this.colors[MaterialPaletteColors.textIcons.value()]
                = colors.getOrDefault("textprimarycolor", black);
    }

    public StyleColor getDarkPrimaryColor() {

        return this.colors[MaterialPaletteColors.darkPrimaryColor.value()];
    }

    public void setDarkPrimaryColor(StyleColor darkPrimaryColor) {
        this.colors[MaterialPaletteColors.darkPrimaryColor.value()] = darkPrimaryColor;
    }

    public StyleColor getAccentColor() {
        return this.colors[MaterialPaletteColors.accentColor.value()];
    }

    public void setAccentColor(StyleColor accentColor) {
        this.colors[MaterialPaletteColors.accentColor.value()] = accentColor;
    }

    public StyleColor getLightPrimaryColor() {
        return this.colors[MaterialPaletteColors.lightPrimaryColor.value()];
    }

    public void setLightPrimaryColor(StyleColor lightPrimaryColor) {
        this.colors[MaterialPaletteColors.lightPrimaryColor.value()] = lightPrimaryColor;
    }

    public StyleColor getPrimaryText() {
        return this.colors[MaterialPaletteColors.primaryText.value()];
    }

    public void setPrimaryText(StyleColor primaryText) {
        this.colors[MaterialPaletteColors.primaryText.value()] = primaryText;
    }

    public StyleColor getSecondaryText() {
        return this.colors[MaterialPaletteColors.secondaryText.value()];
    }

    public void setSecondaryText(StyleColor secondaryText) {
        this.colors[MaterialPaletteColors.secondaryText.value()] = secondaryText;
    }

    public StyleColor getTextIcons() {
        return this.colors[MaterialPaletteColors.textIcons.value()];
    }

    public void setTextIcons(StyleColor textIcons) {
        this.colors[MaterialPaletteColors.textIcons.value()] = textIcons;
    }

    public StyleColor getDividerColor() {
        return this.colors[MaterialPaletteColors.dividerColor.value()];
    }

    public void setDividerColor(StyleColor dividerColor) {
        this.colors[MaterialPaletteColors.dividerColor.value()] = dividerColor;
    }

    public StyleColor get(MaterialPaletteColors colorName) {
        return this.colors[colorName.value()];
    }

}
