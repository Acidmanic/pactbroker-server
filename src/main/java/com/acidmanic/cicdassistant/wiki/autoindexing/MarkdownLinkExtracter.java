/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.autoindexing;

import com.acidmanic.cicdassistant.utility.MarkdownCleanup;
import com.acidmanic.cicdassistant.utility.StringUtils;
import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author diego
 */
public class MarkdownLinkExtracter {

    private final MutableDataSet options = new MutableDataSet();

    public List<String> findAllReferencedNodes(File file) {

        String markdown = readFile(file);

        return findAllReferencedNodes(markdown);
    }

    public List<String> findAllReferencedNodes(String markdown) {

        List<String> links = new ArrayList();

        if (!StringUtils.isNullOrEmpty(markdown)) {

            markdown = new MarkdownCleanup().clean(markdown);

            Parser parser = Parser.builder(options).build();

            Node document = parser.parse(markdown);

            scan(document, links);
        }

        return links;
    }

    private void scan(Node node, List<String> foundLinks) {

        if (node == null) {
            return;
        }

        if (node instanceof Link) {

            Link anchor = (Link) node;

            String anchorText = anchor.getChars().toString();

            List<String> textList = StringUtils.extractPatterns(anchorText, "\\[.*\\]");

            List<String> hrefList = StringUtils.extractPatterns(anchorText, "\\(.*\\)");

            if (textList.size() == 1 && hrefList.size() == 1) {

                String text = StringUtils.stripSides(textList.get(0), "[", "]");

                String href = StringUtils.stripSides(hrefList.get(0), "(", ")");

                if (!StringUtils.isNullOrEmpty(href) && !href.startsWith("http")) {

                    foundLinks.add(href);
                }
            }
        }
        node.getChildren().forEach(child -> scan(child, foundLinks));
    }

    private String readFile(File file) {

        try {

            byte[] data = Files.readAllBytes(file.toPath());

            return new String(data);

        } catch (Exception e) {
        }

        return null;
    }
}
