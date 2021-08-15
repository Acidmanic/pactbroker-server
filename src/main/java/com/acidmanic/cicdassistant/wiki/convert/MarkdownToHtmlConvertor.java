/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert;

import com.acidmanic.cicdassistant.utility.MarkdownCleanup;
import com.acidmanic.cicdassistant.wiki.autoindexing.IndexHtml;
import com.acidmanic.cicdassistant.wiki.autoindexing.IndexHtmlBuilder;
import com.acidmanic.cicdassistant.wiki.autoindexing.WikiIndexTree;
import com.acidmanic.cicdassistant.wiki.convert.autolink.AnchorSource;
import com.acidmanic.cicdassistant.wiki.convert.autolink.AutoAnchorMachine;
import com.acidmanic.cicdassistant.wiki.convert.flexmark.extensions.CodeFormatExtension;
import com.acidmanic.cicdassistant.wiki.convert.flexmark.extensions.LinkManipulationExtension;
import com.acidmanic.cicdassistant.wiki.convert.structure.WikiPage;
import com.acidmanic.cicdassistant.wiki.convert.style.HtmlStyleProvider;
import com.acidmanic.cicdassistant.wiki.convert.style.NullStyleProvider;
import com.acidmanic.cicdassistant.wiki.linkprocessing.LinkManipulator;
import com.acidmanic.cicdassistant.wiki.linkprocessing.LinkTextProvider;
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
    private Function<WikiIndexTree> wikiIndexTreeFactory = () -> null;
    private Function<LinkTextProvider> linkTextProviderFactory = () -> null;

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

    public MarkdownToHtmlConvertor useIndexTree(Function<WikiIndexTree> factory) {

        this.wikiIndexTreeFactory = factory;

        return this;
    }

    public MarkdownToHtmlConvertor useLinkTextProvider(Function<LinkTextProvider> factory) {

        this.linkTextProviderFactory = factory;

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

        String wikiHtml = htmlRenderer.render(document);

        String styles = this.styleProvider.getHeadInjectableHtml(styleName);

        this.anchorSources.forEach(s -> s.preProcessInputString(wikiHtml));

        WikiPage wikiPage = new WikiPage()
                .indexHtml(createIndexTag())
                .scripts(InMemoryResources.COPY_TO_CLIPBOARD_JS + InMemoryResources.TOAST_COMPONENT)
                .styles(styles)
                .styleProvider(styleProvider)
                .wikiHtml(wikiHtml);

        String html = wikiPage.toString();

        AutoAnchorMachine anchorMachine = new AutoAnchorMachine(this.anchorSources);

        html = anchorMachine.scan(html);

        return html;

    }

    private String createIndexTag() {

        WikiIndexTree indexTree = this.wikiIndexTreeFactory == null ? null : this.wikiIndexTreeFactory.perform();

        if (indexTree != null) {

            LinkTextProvider textProvider = this.linkTextProviderFactory == null ? null : this.linkTextProviderFactory.perform();
            if (textProvider != null) {

                IndexHtmlBuilder builder = new IndexHtmlBuilder()
                        .limitLevelsTo(3)
                        .use(this.linkManipulator)
                        .use(textProvider)
                        .addHeads(indexTree.getHeads());

                if (!indexTree.getMiscellaneousNodes().isEmpty()) {

                    builder.addtMiscellaneous(indexTree.getMiscellaneousNodes());
                }

                IndexHtml indexHtml = builder.build();

                String html = indexHtml.getHtmlContent();

                return html;
            }
        }
        return "";
    }

}
