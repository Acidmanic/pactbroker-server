/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert;

import com.acidmanic.cicdassistant.utility.MarkdownCleanup;
import com.acidmanic.cicdassistant.wiki.convert.autolink.AnchorSource;
import com.acidmanic.cicdassistant.wiki.convert.autolink.AutoAnchorMachine;
import com.acidmanic.cicdassistant.wiki.convert.flexmark.extensions.CodeFormatExtension;
import com.acidmanic.cicdassistant.wiki.convert.flexmark.extensions.LinkManipulationExtension;
import com.acidmanic.cicdassistant.wiki.convert.style.HtmlStyleProvider;
import com.acidmanic.cicdassistant.wiki.convert.style.NullStyleProvider;
import com.acidmanic.cicdassistant.wiki.linkprocessing.LinkManipulator;
import com.acidmanic.delegates.Function;
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
    private HtmlStyleProvider styleProvider;
    private LinkManipulator linkManipulator = s -> s;
    
    
    public MarkdownToHtmlConvertor() {

        this.styleProvider = new NullStyleProvider();
    }

    public MarkdownToHtmlConvertor addAnchorSource(AnchorSource provider) {

        this.anchorSources.add(provider);

        return this;
    }

    public MarkdownToHtmlConvertor setStyleProvider(HtmlStyleProvider styleProvider) {

        this.styleProvider = styleProvider;

        return this;
    }

    public MarkdownToHtmlConvertor addExtension(Extension extension) {

        this.extensions.add(extension);

        return this;
    }

    public MarkdownToHtmlConvertor setLinkManipulator(LinkManipulator manipulator) {

        this.linkManipulator = manipulator;

        return this;
    }

    private MutableDataSet provideOptions() {

        MutableDataSet options = new MutableDataSet();

        List<Extension> allExtentions = new ArrayList<>();

        allExtentions.addAll(Arrays.asList(
                StrikethroughExtension.create(),
                TablesExtension.create(),
                TocExtension.create(),
                new CodeFormatExtension(),
                EmojiExtension.create(),
                new LinkManipulationExtension(linkManipulator)
        ));

        allExtentions.addAll(this.extensions);

        options.set(Parser.EXTENSIONS, allExtentions);

        return options;

    }

    public String convert(String markdown) {

        return convert(markdown, this.styleProvider.getDefaultName());
    }

    public String convert(String markdown, String styleName) {

        markdown = new MarkdownCleanup().clean(markdown);

        MutableDataSet options = provideOptions();

        Parser parser = Parser.builder(options).build();

        HtmlRenderer htmlRenderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse(markdown);

        String body = htmlRenderer.render(document);

        String styles = this.styleProvider.getHeadInjectableHtml(styleName);

        this.anchorSources.forEach(s -> s.preProcessInputString(body));

        String html = ""
                + "<html>"
                + "<head>"
                + styles
                + "</head>"
                + "<body>"
                + createThemeContainer()
                + InMemoryResources.COPY_TO_CLIPBOARD_JS
                + InMemoryResources.TOAST_COMPONENT
                + body
                + "</body>"
                + "</html>";

        AutoAnchorMachine anchorMachine = new AutoAnchorMachine(this.anchorSources);

        html = anchorMachine.scan(html);

        return html;

    }

    private String createThemeContainer() {

        String container = "<div class =\"theme-container\">";

        for (String themeName : this.styleProvider.getNames()) {

            String colorCode = this.styleProvider.getStyleFlagColorCode(themeName);

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
