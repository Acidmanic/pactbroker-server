/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open  the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.structure;

import com.acidmanic.cicdassistant.html.Style;
import com.acidmanic.cicdassistant.html.StyleSheet;
import com.acidmanic.cicdassistant.html.Tag;

/**
 *
 * @author diego
 */
public class WikiStyleSheet extends StyleSheet {

    public WikiStyleSheet() {

        this.addChild(bodyStyle());
        this.addChild(aAhourStyle());
        this.addChild(tableStyle());
        this.addChild(tdStyle());
        this.addChild(trStyle());
        addSingleColorStyle(".json-string", "#689F38");
        addSingleColorStyle(".json-number", "#FF5722");
        addSingleColorStyle(".json-key", "#448AFF");
        this.addChild(jsonObjectCodeStyle());
        this.addChild(btnCopyStyle());
        this.addChild(btnCopyActiveStyle());
        this.addChild(hiddenDataStyle());
        this.addChild(codeMessageStyle());
        this.addChild(themeItemStyle());
        this.addChild(themeContainerStyle());
        this.addChild(themeItemHoverStyle());
        this.addChild(wikiIndexStyle());
    }

    private Style bodyStyle() {

        Style style = new Style("body");

        style.addProperty("font-family", "-apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Noto Sans\", Ubuntu, Cantarell, \"Helvetica Neue\", sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\", \"Noto Color Emoji\"");

        style.addProperty("padding-left", "5%");

        style.addProperty("padding-right", "5%");

        return style;
    }

    private Style aAhourStyle() {

        Style style = new Style("a,a:hover");

        style.addProperty("text-decoration", "none");

        style.addProperty("text-shadow", "0 0 10px #CCCCCC0F");

        style.addProperty("vertical-align", "top");

        return style;
    }

    private Style tableStyle() {

        Style style = new Style("table");

//        style.addProperty("max-width", "70%");
        style.addProperty("font-size", "0.9em");
        style.addProperty("display", "inline-table");

        style.addProperty("border", "1px solid black");

        return style;
    }

    private Style tdStyle() {

        Style style = new Style("td");

        style.addProperty("padding", "6pt");

        return style;
    }

    private Style trStyle() {

        Style style = new Style("tr");

        style.addProperty("border", "solid 1px #a2aab0");

        style.addProperty("padding", "6pt");

        return style;
    }

    private void addSingleColorStyle(String name, String code) {

        Style style = new Style(name);

        style.addProperty("color", code);

        this.addChild(style);
    }

    private Style jsonObjectCodeStyle() {

        Style style = new Style(".json-object,code");

        style.addProperty("background-color", "#0000001A");

        style.addProperty("border", "solid 1px #a2aab0");

        style.addProperty("font-size", "0.85em");

        style.addProperty("display", "inline-block");

        style.addProperty("padding", "5pt");

        style.addProperty("border-radius", "3pt");

        this.addChild(style);

        return style;
    }

    private Style btnCopyStyle() {

        Style style = new Style(".btn-copy, .btn-copy:active");

//        style.addProperty("width", "27pt");
//        style.addProperty("height", "13pt");
        style.addProperty("border-radius", "3pt");
        style.addProperty("margin", "0pt");
        style.addProperty("paddiing-top", "3pt");
        style.addProperty("paddiing-bottom", "3pt");
        style.addProperty("paddiing-left", "5pt");
        style.addProperty("paddiing-right", "5pt");
        style.addProperty("border", "solid 1px #7b7b7b");
        style.addProperty("cursor", "pointer");
        style.addProperty("text-align", "center");
        style.addProperty("font-size", "9pt");
        style.addProperty("color", "#1a8cf0");
        this.addChild(style);

        return style;
    }

    private Style btnCopyActiveStyle() {

        Style style = new Style(".btn-copy:active");

        style.addProperty("background-color", "2d3233");
        style.addProperty("text-shadow", "0px 0px 3pt #3b8a65");
        style.addProperty("border", "solid 1px #535353");

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

        Style style = new Style(".code-message");

        style.addProperty("display", "block");
        style.addProperty("visibility", "hidden");
        style.addProperty("padding-top", "-2pt");
        style.addProperty("margin-righ", "20pt");
        style.addProperty("margin-bottom", "6pt");
        style.addColorProperty("color", "#dbffe8");
        style.addProperty("background-image", "linear-gradient(to right, #4fac9fa3 , #62ff0a0a)");
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

        Style style = new Style(".theme-item:hover");

        style.addProperty("width", "20pt");
        style.addProperty("height", "20pt");
        style.addProperty("border", "solid 1px #000000");
        style.addProperty("border-radius", "20pt");

        return style;
    }

    private Style themeContainerStyle() {

        Style style = new Style(".theme-container");

        style.addProperty("cursor", "pointer");
        style.addProperty("left", "5pt");
        style.addProperty("top", "5pt");
        style.addProperty("position", "absolute");
        style.addProperty("display", "block");

        return style;
    }

    private Style wikiIndexStyle() {

        Style wikiIndexListItem = new Style(".auto-index li");
        wikiIndexListItem.addProperty("text-shadow", "0px 0px 100pt #f9f9f98a");
        wikiIndexListItem.addProperty("color", "white");
        wikiIndexListItem.addProperty("list-style-type", "disclosure-closed");

        return wikiIndexListItem;
    }
}
