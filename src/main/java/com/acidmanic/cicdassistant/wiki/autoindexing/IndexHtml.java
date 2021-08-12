/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.autoindexing;

import com.acidmanic.cicdassistant.html.A;
import com.acidmanic.cicdassistant.html.Li;
import com.acidmanic.cicdassistant.html.RawString;
import com.acidmanic.cicdassistant.html.Ul;
import com.acidmanic.cicdassistant.wiki.linkprocessing.LinkManipulator;
import com.acidmanic.cicdassistant.wiki.linkprocessing.LinkTextProvider;
import com.acidmanic.cicdassistant.wiki.linkprocessing.MarkdownLinkTextProvider;
import java.util.List;
import org.eclipse.jgit.util.StringUtils;

/**
 *
 * @author diego
 */
public class IndexHtml {

    private final int levelsLimit;
    private final String htmlContent;

    private final LinkManipulator linkManipulator;
    private final LinkTextProvider linkTextProvider;
    private final LinkTextProvider backupLinkTextProvider = new MarkdownLinkTextProvider();

    public IndexHtml(List<WebNode> heads,
            int levels,
            LinkManipulator linkManipulator,
            LinkTextProvider linkTextProvider) {

        this.levelsLimit = levels;
        this.linkManipulator = linkManipulator;
        this.linkTextProvider = linkTextProvider;

        Ul menu = new Ul();

        menu.addCssClass("auto-index");
        menu.addCssClass("auto-index-menu");

        for (WebNode head : heads) {

            Li subMenu = createSubMenu(head, 0);

            menu.addChild(subMenu);
        }

        this.htmlContent = menu.toString();
    }

    private Li createSubMenu(WebNode node, int level) {

        Li li = new Li();

        boolean hasChildren = level < this.levelsLimit && !node.isLeaf();

        if (node instanceof NoneLinkWebNode) {

            RawString text = new RawString(((NoneLinkWebNode) node).getTitle());

            li.addChild(text);

            li.addCssClass("auto-index")
                    .addCssClass("auto-index-title");

        } else {
            A link = createLink(node);

            link.addCssClass("auto-index");

            link.addCssClass(hasChildren ? "auto-index-title-level" + level : "auto-index-leaf");

            li.addChild(link);
        }

        if (hasChildren) {

            Ul childContainer = new Ul();

            childContainer.addCssClass("auto-index auto-index-subset-level" + level);

            for (WebNode child : node.getReferences()) {

                Li childLi = createSubMenu(child, level + 1);

                childContainer.addChild(childLi);

            }

            li.addChild(childContainer);
        }
        return li;
    }

    private A createLink(WebNode node) {

        String text = node.getFile().getName();

        text = this.linkTextProvider.getTextFor(node.getKey());

        if (StringUtils.isEmptyOrNull(text)) {
            text = this.backupLinkTextProvider.getTextFor(node.getKey());
        }

        String href = this.linkManipulator.manipulate(node.getKey());

        A a = new A();

        a.setHref(href);

        a.addChild(new RawString(text));

        return a;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

}
