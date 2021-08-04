/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.html.styles;

import com.acidmanic.cicdassistant.utility.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author diego
 */
public class StyleProcessor {

    private final String styleBase;
    private final List<String> extractedColorCodes;
    private final List<StyleColor> normalizedColors;

    private StyleColor tintColor;

    public StyleProcessor(String styleBase) {

        this.styleBase = styleBase;

        this.extractedColorCodes = StringUtils.extractPatterns(styleBase, "#[a-fA-F0-9]+");

        List<StyleColor> extractedColors = listAllColors(extractedColorCodes);

        this.tintColor = StyleColorUtils.average(extractedColors);
        
        System.out.println("TINT: " + this.tintColor.toCode());

        this.normalizedColors = StyleColorUtils.scaleOverColor(extractedColors, tintColor);
    }

    private List<StyleColor> listAllColors(List<String> codes) {

        List<StyleColor> colors = new ArrayList<>();

        codes.forEach(code -> colors.add(StyleColor.fromCode(code)));

        return colors;
    }

    public StyleColor getTintColor() {
        return tintColor;
    }

    public void setTintColor(StyleColor tintColor) {
        this.tintColor = tintColor;
    }

    public String getTintedStyle() {

        List<StyleColor> colors = StyleColorUtils.scaleIntoFactor(
                this.normalizedColors,
                this.tintColor);

        String tintedStyle = this.styleBase;

        List<Integer> indexes = createIndexes(colors.size());

        indexes.sort((i1, i2) -> extractedColorCodes.get(i1).length() - extractedColorCodes.get(i2).length());

        for (int index : indexes) {

            String find = this.extractedColorCodes.get(index);

            String replace = colors.get(index).toCode();

            tintedStyle = tintedStyle.replaceAll(find, replace);
        }

        return tintedStyle;

    }

    private List<Integer> createIndexes(int count) {

        ArrayList<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            indexes.add(i);
        }
        return indexes;
    }

}
