/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert;

import com.acidmanic.cicdassistant.html.styles.StyleColor;
import com.acidmanic.cicdassistant.html.styles.StyleColorUtils;
import com.acidmanic.cicdassistant.html.styles.StyleProcessor;
import com.acidmanic.cicdassistant.utility.MarkdownCleanup;
import com.acidmanic.cicdassistant.wiki.convert.autolink.AnchorSource;
import com.acidmanic.cicdassistant.wiki.convert.autolink.AutoAnchorMachine;
import com.acidmanic.cicdassistant.wiki.convert.flexmark.extensions.CodeFormatExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.misc.Extension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author diego
 */
public class MarkdownToHtmlConvertor {

    private final List<AnchorSource> anchorSources = new ArrayList<>();
    private final List<Extension> extensions = new ArrayList<>();
    private StyleProcessor styleProcessor;
    private String themeName = "DarkGreen";

    public MarkdownToHtmlConvertor addAnchorSource(AnchorSource provider) {

        this.anchorSources.add(provider);

        return this;
    }

    public MarkdownToHtmlConvertor addExtension(Extension extension) {

        this.extensions.add(extension);

        return this;
    }

    public MarkdownToHtmlConvertor setTintColor(StyleColor tintColor) {

        this.styleProcessor.setTintColor(tintColor);

        return this;
    }

    public MarkdownToHtmlConvertor setThemeName(String themeName) {

        this.themeName = themeName;

        updateStyleProcessor();

        return this;
    }

    public MarkdownToHtmlConvertor setTintColor(String tintColorCode) {

        this.styleProcessor.setTintColor(StyleColor.fromCode(tintColorCode));

        return this;
    }

    public MarkdownToHtmlConvertor() {

        updateStyleProcessor();

    }

    private MutableDataSet provideOptions() {

        MutableDataSet options = new MutableDataSet();

        List<Extension> allExtentions = new ArrayList<>();

        allExtentions.addAll(Arrays.asList(//                        AnchorLinkExtension.create(), //Anchor headlines
                StrikethroughExtension.create(), //Corresponds to strikethrough
                TablesExtension.create(), //Compatible with tables
                TocExtension.create(),// [TOC]Generate a table of contents in the part of
                new CodeFormatExtension(),
                EmojiExtension.create()
        ));

        allExtentions.addAll(this.extensions);

        options.set(Parser.EXTENSIONS, allExtentions);

        return options;

    }

    public String convert(String markdown) {

        markdown = new MarkdownCleanup().clean(markdown);

        MutableDataSet options = provideOptions();

        Parser parser = Parser.builder(options).build();

        HtmlRenderer htmlRenderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse(markdown);

        String body = htmlRenderer.render(document);

        String styles = this.getStyles();

        this.anchorSources.forEach(s -> s.preProcessInputString(body));

        String html = "<html><head>" + styles
                + "</head>"
                + "<body>"
                + createThemeContainer()
                + InMemoryResources.COPY_TO_CLIPBOARD_JS
                + InMemoryResources.TOAST_COMPONENT
                + body + "</body></html>";

        AutoAnchorMachine anchorMachine = new AutoAnchorMachine(this.anchorSources);

        html = anchorMachine.scan(html);

        return html;

    }

    private String getStyles() {

        StringBuilder styles = new StringBuilder();

        styles.append("<style>");

        styles.append(this.styleProcessor.getTintedStyle());

        styles.append("</style>");

        return styles.toString();
    }

    private void updateStyleProcessor() {

        String style = InMemoryResources.WIKI_STYLES_GREEN;

        if (InMemoryResources.NAMED_THEMES.containsKey(this.themeName)) {

            style = InMemoryResources.NAMED_THEMES.get(this.themeName);
        }

        this.styleProcessor = new StyleProcessor(style);
    }

    private String createThemeContainer() {

        String container = "<div class =\"theme-container\">";

        for (String themeName : InMemoryResources.NAMED_THEMES.keySet()) {

            String colorCode = InMemoryResources.NAMED_THEMES_FLAGS.get(themeName);

            System.out.println("COLOR: " + colorCode);

            container += "<a title=\"" + themeName + "\" href=\"?theme=" + themeName + "\">"
                    + "<div class=\"theme-item\" "
                    + "style=\"background-color: " + colorCode + "\">"
                    + "</div>"
                    + "</a>";
        }

        container += "</div>";

        return container;
    }

}
