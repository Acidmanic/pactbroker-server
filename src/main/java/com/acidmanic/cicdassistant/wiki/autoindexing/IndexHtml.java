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
import java.util.List;

/**
 *
 * @author diego
 */
public class IndexHtml {

    private final int levelsLimit;
    private final String htmlContent;

    private final LinkManipulator linkManipulator;

    public IndexHtml(List<WebNode> heads) {
        this(heads, Integer.MAX_VALUE, s -> s);
    }

    public IndexHtml(List<WebNode> heads, int levels) {
        this(heads, levels, s -> s);
    }

    public IndexHtml(List<WebNode> heads, LinkManipulator linkManipulator) {
        this(heads, Integer.MAX_VALUE, linkManipulator);
    }

    public IndexHtml(List<WebNode> heads, int levels, LinkManipulator linkManipulator) {

        this.levelsLimit = levels;
        this.linkManipulator = linkManipulator;

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

        A link = createLink(node);

        link.addCssClass("auto-index");

        boolean hasChildren = level < this.levelsLimit && !node.isLeaf();

        link.addCssClass(hasChildren ? "auto-index-title-level" + level : "auto-index-leaf");

        li.addChild(link);

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

        text = clearAsTitle(text);

        String href = this.linkManipulator.manipulate(node.getKey());

        A a = new A();

        a.setHref(href);

        a.addChild(new RawString(text));

        return a;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    private String clearAsTitle(String text) {

        if (text.toLowerCase().endsWith(".md")) {
            text = text.substring(0, text.length() - 3);
        }

        char[] chars = text.toCharArray();

        StringBuilder sb = new StringBuilder();

        boolean lastWhiteSpace = true;

        for (char c : chars) {

            if (Character.isWhitespace(c)) {

                if (!lastWhiteSpace) {

                    sb.append(" ");
                }
                lastWhiteSpace = true;

            } else {

                if (Character.isAlphabetic(c) || Character.isDigit(c)) {

                    sb.append(lastWhiteSpace ? Character.toUpperCase(c) : Character.toLowerCase(c));
                }
                lastWhiteSpace = false;
            }
        }

        return sb.toString();
    }

}
