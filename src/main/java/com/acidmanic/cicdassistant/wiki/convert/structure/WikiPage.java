/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.structure;

import com.acidmanic.cicdassistant.html.A;
import com.acidmanic.cicdassistant.html.Div;
import com.acidmanic.cicdassistant.html.Html;
import com.acidmanic.cicdassistant.html.Link;
import com.acidmanic.cicdassistant.html.RawHtml;
import com.acidmanic.cicdassistant.html.Script;
import com.acidmanic.cicdassistant.html.StyleSheet;
import com.acidmanic.cicdassistant.wiki.convert.style.HtmlStyleProvider;

/**
 *
 * @author diego
 */
public class WikiPage {

    private String styles = "";
    private String scripts = "";
    private String indexHtml = "";
    private String wikiHtml = "No Content";
    private HtmlStyleProvider styleProvider;
    private final String CSS_THEME_CONTAINER = "theme-container";
    private final String CSS_THEME_ITEM = "theme-item";

    public WikiPage() {
    }

    public WikiPage styles(String styles) {
        this.styles = styles;
        return this;
    }

    public WikiPage scripts(String scripts) {
        this.scripts = scripts;
        return this;
    }

    public WikiPage indexHtml(String indexHtml) {
        this.indexHtml = indexHtml;
        return this;
    }

    public WikiPage wikiHtml(String wikiHtml) {
        this.wikiHtml = wikiHtml;
        return this;
    }

    public WikiPage styleProvider(HtmlStyleProvider provider) {
        this.styleProvider = provider;
        return this;
    }

    @Override
    public String toString() {

        String bootstrapCssHref = "/../assets/bootstrap5/bootstrap.min.css";
        String bootstrapJsSrc = "/../assets/bootstrap5/bootstrap.bundle.min.js";

        Html html = new Html();
        // set title

        // add bootstrap
        Link bootstrapCss = new Link();
        bootstrapCss.setHref(bootstrapCssHref);
        bootstrapCss.setRel("stylesheet");

        Script bootStrapScript = new Script();
        bootStrapScript.setSrc(bootstrapJsSrc);

        html.getHead().addChild(bootstrapCss);
        html.getHead().addChild(bootStrapScript);

        // add builtind styles
        StyleSheet builtinStyles = new WikiStyleSheet();

        html.getHead().addChild(builtinStyles);

        RawHtml injections = new RawHtml(this.scripts + this.styles);

        html.getHead().addChild(injections);

        // Design body
        Div divContainer = new Div();

        html.getBody().addChild(divContainer);

        divContainer.setCssClass("container");

        Div themeContainer = createThemeContainer();

        divContainer.addChild(themeContainer);

        Div contentRow = new Div();

        contentRow.setCssClass("row");

        divContainer.addChild(contentRow);

        // Add wiki and index columns
        Div divWiki = new Div();
        Div divIndex = new Div();

        contentRow.addChild(divWiki).addChild(divIndex);

        //Design div wiki
        divWiki.setCssClass("col-md-8 col-sm-12");

        RawHtml wikiContent = new RawHtml(this.wikiHtml);

        divWiki.addChild(wikiContent);

        //Design div index
        divIndex.setCssClass("col-md-4 cols-sm-12");

        RawHtml indexContent = new RawHtml(this.indexHtml);

        divIndex.addChild(indexContent);

        return html.toString();
    }

    private Div createThemeContainer() {

        Div containerDiv = new Div();

        containerDiv.setCssClass(CSS_THEME_CONTAINER);

        for (String themeName : this.styleProvider.getNames()) {

            String colorCode = this.styleProvider.getStyleFlagColorCode(themeName);

            A themeAnchor = new A();

            themeAnchor.setTitle(themeName);
            themeAnchor.setHref("?theme=" + themeName);

            Div themeButton = new Div();

            themeButton.setCssClass(CSS_THEME_ITEM);
            themeButton.setStyle("background-color:" + colorCode);

            themeAnchor.addChild(themeButton);

            containerDiv.addChild(themeAnchor);

        }
        return containerDiv;
    }

}
