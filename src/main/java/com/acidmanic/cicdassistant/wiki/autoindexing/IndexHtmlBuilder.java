/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.autoindexing;

import com.acidmanic.cicdassistant.wiki.linkprocessing.LinkManipulator;
import com.acidmanic.cicdassistant.wiki.linkprocessing.LinkTextProvider;
import com.acidmanic.cicdassistant.wiki.linkprocessing.MarkdownLinkTextProvider;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author diego
 */
public class IndexHtmlBuilder {

    private int levelsLimit = Integer.MAX_VALUE;

    private LinkManipulator linkManipulator = s -> s;

    private LinkTextProvider linkTextProvider = new MarkdownLinkTextProvider();

    private List<WebNode> heads = new ArrayList<>();

    public IndexHtmlBuilder limitLevelsTo(int levels) {

        this.levelsLimit = levels;

        return this;
    }

    public IndexHtmlBuilder use(LinkManipulator linkManipulator) {

        this.linkManipulator = linkManipulator;

        return this;
    }

    public IndexHtmlBuilder use(LinkTextProvider linkTextProvider) {

        this.linkTextProvider = linkTextProvider;

        return this;
    }

    public IndexHtmlBuilder addHeads(Collection<WebNode> heads) {

        this.heads.addAll(heads);

        return this;
    }

    public IndexHtmlBuilder addtMiscellaneous(Collection<WebNode> nodes, String title) {

        WebNode miscNode = new NoneLinkWebNode(title);

        for (WebNode node : nodes) {
            // each node is a singular node
            // so it has no references and no sitations
            // so its safe to use a singular clone. its identical
            // if you use the node itself, it will get sited by miscNode
            // so the source data will change and the node would not be 
            // singular anymore. it will be a singular not showing up as header!
            WebNode nodeClone = node.singularClone();

            miscNode.references(nodeClone);

            nodeClone.sitedBy(miscNode);
        }

        this.heads.add(miscNode);

        return this;
    }

    public IndexHtmlBuilder addtMiscellaneous(Collection<WebNode> nodes) {

        return this.addtMiscellaneous(nodes, "Miscellaneous");
    }

    public IndexHtml build() {

        return new IndexHtml(heads, levelsLimit, linkManipulator, linkTextProvider);
    }

}
