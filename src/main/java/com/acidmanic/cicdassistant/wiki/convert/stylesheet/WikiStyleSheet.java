/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.stylesheet;

import com.acidmanic.cicdassistant.html.Style;
import com.acidmanic.cicdassistant.html.StyleSheet;
import com.acidmanic.cicdassistant.html.theme.MaterialPalette;
import com.acidmanic.cicdassistant.html.theme.MaterialPaletteColors;
import com.acidmanic.cicdassistant.html.theme.MaterialStyle;

/**
 *
 * @author diego
 */
public class WikiStyleSheet extends StyleSheet {

    private final MaterialPalette palette;

    public WikiStyleSheet(MaterialPalette palette) {
        this.palette = palette;

        this.addChild(bodyStyle());
        this.addChild(aAhourStyle());
        this.addChild(tableStyle());
        this.addChild(tdStyle());
        this.addChild(theadStyle());
        this.addChild(theadTrStyle());
        this.addChild(trStyle());
        this.addChild(trEvenStyle());
        this.addChild(trOddStyle());
        addSingleColorStyle(".json-string", "#689F38");
        addSingleColorStyle(".json-number", "#FF5722");
        addSingleColorStyle(".json-key", "#448AFF");
        this.addChild(jsonObjectCodeStyle());
        this.addChild(btnCopyStyle());
        this.addChild(btnCopyActiveStyle());
        this.addChild(hiddenDataStyle());
        this.addChild(codeMessageStyle());
        this.addChild(themeItemStyle());
        this.addChild(themeContaonerStyle());
        this.addChild(themeItemHoverStyle());
        this.addChild(hrStyle());
    }

    private Style bodyStyle() {

        MaterialStyle style = new MaterialStyle(palette, "body");

        style.addProperty("font-family", "-apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Noto Sans\", Ubuntu, Cantarell, \"Helvetica Neue\", sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\", \"Noto Color Emoji\"");

        style.addColorProperty("background-color", MaterialPaletteColors.lightPrimaryColor)
                .addColorProperty("color", MaterialPaletteColors.textIcons);

        style.addProperty("padding-left", "5%");
        style.addProperty("padding-right", "5%");

        return style;
    }

    private Style aAhourStyle() {

        MaterialStyle style = new MaterialStyle(palette, "a,a:hover");

        style.addProperty("text-decoration", "none");

        style.addColorProperty("color", MaterialPaletteColors.accentColor);

        style.addProperty("text-shadow", "0 0 10px #CCCCCC0F");

        style.addProperty("vertical-align", "top");

        return style;
    }

    private Style tableStyle() {

        MaterialStyle style = new MaterialStyle(palette, "table");

        style.addProperty("max-width", "70%");
        style.addProperty("font-size", "0.9em");
        style.addProperty("display", "inline-table");

        style.addProperty("border", "1px solid black");
        style.addColorProperty("border-color", MaterialPaletteColors.dividerColor);

        return style;
    }

    private Style tdStyle() {

        MaterialStyle style = new MaterialStyle(palette, "td");

        style.addProperty("padding", "6pt");

        return style;
    }

    private Style theadStyle() {

        MaterialStyle style = new MaterialStyle(palette, "thead");

        style.addColorProperty("background-color", MaterialPaletteColors.primaryColor);

        return style;
    }

    private Style theadTrStyle() {

        MaterialStyle style = new MaterialStyle(palette, "thead tr:nth-child(odd)");

        style.addProperty("background-color", "#ffffff69");

        return style;
    }

    private Style trStyle() {

        MaterialStyle style = new MaterialStyle(palette, "tr");

        style.addProperty("border", "solid 1px #000000");
        style.addColorProperty("border-color", MaterialPaletteColors.dividerColor);
        style.addColorProperty("color", MaterialPaletteColors.primaryText);
        return style;
    }

    private Style trEvenStyle() {

        MaterialStyle style = new MaterialStyle(palette, "tr:nth-child(even)");

        style.addColorProperty("color", MaterialPaletteColors.textIcons);

        style.addColorProperty("background-color", MaterialPaletteColors.darkPrimaryColor);

        return style;
    }

    private Style trOddStyle() {

        MaterialStyle style = new MaterialStyle(palette, "tr:nth-child(odd)");

        style.addColorProperty("background-color", MaterialPaletteColors.lightPrimaryColor);

        return style;
    }

    private void addSingleColorStyle(String name, MaterialPaletteColors color) {

        MaterialStyle style = new MaterialStyle(palette, name);

        style.addColorProperty("color", color);

        this.addChild(style);
    }

    private void addSingleColorStyle(String name, String code) {

        Style style = new Style(name);

        style.addProperty("color", code);

        this.addChild(style);
    }

    private Style jsonObjectCodeStyle() {

        MaterialStyle style = new MaterialStyle(palette, ".json-object,code");

        style.addProperty("background-color", "#00000061");
        style.addProperty("border", "solid 1px #000000");
        style.addColorProperty("border-color", MaterialPaletteColors.dividerColor);
        style.addColorProperty("color", MaterialPaletteColors.dividerColor);
        style.addProperty("display", "inline-block");
        style.addProperty("padding", "5pt");

        this.addChild(style);

        return style;
    }

    private Style btnCopyStyle() {

        MaterialStyle style = new MaterialStyle(palette, ".btn-copy, .btn-copy:active");

        style.addProperty("width", "27pt");
        style.addProperty("height", "13pt");
        style.addProperty("border-radius", "3pt");
        style.addProperty("margin", "0pt");
        style.addProperty("border", "solid 1px #000000");
        style.addColorProperty("border-color", MaterialPaletteColors.dividerColor);
        style.addProperty("cursor", "pointer");
        style.addProperty("text-align", "center");
        style.addProperty("font-size", "9pt");
        style.addColorProperty("color", MaterialPaletteColors.dividerColor);
        this.addChild(style);

        return style;
    }

    private Style btnCopyActiveStyle() {

        MaterialStyle style = new MaterialStyle(palette, ".btn-copy:active");

        style.addProperty("border", "solid 1px #000000");
        style.addColorProperty("border-color", MaterialPaletteColors.dividerColor);
        style.addColorProperty("background-color", MaterialPaletteColors.accentColor);
        this.addChild(style);

        return style;
    }

    private Style hiddenDataStyle() {

        Style style = new Style(".hidden-data");

        style.addProperty("width", "0px");
        style.addProperty("height", "0px");
        style.addProperty("position", "absolute");
        style.addProperty("top", "0px");
        style.addProperty("left", "0px");
        style.addProperty("border", "none");
        style.addProperty("outline", "none");
        style.addProperty("margin", "0px");
        style.addProperty("padding", "0px");

        return style;
    }

    private Style codeMessageStyle() {

        MaterialStyle style = new MaterialStyle(palette, ".code-message");

        style.addProperty("display", "block");
        style.addProperty("visibility", "hidden");
        style.addProperty("padding-top", "-2pt");
        style.addProperty("margin-righ", "20pt");
        style.addProperty("margin-bottom", "6pt");
        style.addColorProperty("color", MaterialPaletteColors.textIcons);
        style.addFadeToRightLineaGradiantProperty("background-image", MaterialPaletteColors.primaryText);
        style.addProperty("text-align", "center");
        style.addProperty("padding-bottom", "2pt");
        style.addProperty("padding-top", "2pt");
        style.addProperty("border-radius", "3pt");

        return style;
    }

    private Style themeItemStyle() {

        Style style = new Style(".theme-item");

        style.addProperty("width", "20pt");
        style.addProperty("height", "3pt");
        style.addProperty("position", "relative");

        return style;
    }

    private Style themeItemHoverStyle() {

        MaterialStyle style = new MaterialStyle(this.palette, ".theme-item:hover");

        style.addProperty("width", "20pt");
        style.addProperty("height", "20pt");
        style.addProperty("border", "solid 1px #000000");
        style.addColorProperty("border-color", MaterialPaletteColors.dividerColor);
        style.addProperty("border-radius", "20pt");

        return style;
    }

    private Style themeContaonerStyle() {

        Style style = new Style(".theme-container");

        style.addProperty("cursor", "pointer");
        style.addProperty("left", "5pt");
        style.addProperty("top", "5pt");
        style.addProperty("position", "absolute");
        style.addProperty("display", "block");

        return style;
    }

    private Style hrStyle() {

        MaterialStyle style = new MaterialStyle(this.palette, "hr");

        style.addProperty("border-width", "1pt");
        style.addProperty("opacity", "0.4");
        style.addColorProperty("background-color", MaterialPaletteColors.dividerColor);

        return style;
    }
}
