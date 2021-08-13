/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.autoindexing;

import com.acidmanic.cicdassistant.utility.PathHelpers;
import com.acidmanic.cicdassistant.wiki.linkprocessing.LinkTextProvider;
import com.acidmanic.delegates.arg3.Action;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author diego
 */
public class MarkdownWikiIndexTree implements LinkTextProvider, WikiIndexTree {

    private final File baseDirectory;

    private final HashMap<String, WebNode> keys;

    private final HashMap<String, String> titles;

    public MarkdownWikiIndexTree(File baseDirectory) {
        this.baseDirectory = baseDirectory;

        this.keys = new HashMap<>();

        this.titles = new HashMap<>();

        indexDirectory();

    }

    @Override
    public List<WebNode> getHeads() {

        ArrayList<WebNode> heads = new ArrayList<>();

        for (WebNode node : keys.values()) {

            if (node.isHead()) {

                heads.add(node);
            }
        }
        return heads;
    }

    @Override
    public List<WebNode> getMiscellaneousNodes() {

        ArrayList<WebNode> heads = new ArrayList<>();

        for (WebNode node : keys.values()) {

            if (node.isSingular()) {

                heads.add(node);
            }
        }
        return heads;
    }

    private void indexDirectory() {

        // Scan all files, and create nodes
        indexDirectory(baseDirectory);

        linkNodes(this.keys);
    }

    private void indexDirectory(File directory) {

        if (directory.isDirectory()) {

            File[] files = directory.listFiles();

            for (File file : files) {

                indexDirectory(file);
            }

        } else {
            indexFile(directory);
        }

    }

    private void indexFile(File file) {

        if (file.isFile()) {

            if (file.getName().toLowerCase().endsWith(".md")) {

                String refKey = filePathToLink(file);

                if (!this.keys.containsKey(refKey)) {

                    this.keys.put(refKey, new WebNode(refKey, file));
                }

            }
        }
    }

    private void walkThroughNodePairs(HashMap<String, WebNode> nodes,
            HashMap<WebNode, List<MarkdownLink>> nodeReferences,
            Action<WebNode, WebNode, PathHelpers.PathRelation> scanner) {

        for (WebNode node : nodes.values()) {

            List<MarkdownLink> referencedLinks = nodeReferences.get(node);

            for (MarkdownLink referencedLink : referencedLinks) {

                if (nodes.containsKey(referencedLink.getHref())) {

                    WebNode referencedNode = nodes.get(referencedLink.getHref());

                    PathHelpers.PathRelation relation = new PathHelpers()
                            .relationOfRelativePaths(node.getFile().toPath(),
                                    referencedNode.getFile().toPath());

                    scanner.perform(node, referencedNode, relation);
                }
            }
        }
    }

    private void linkNodes(HashMap<String, WebNode> nodes) {

        HashMap<WebNode, List<MarkdownLink>> nodeReferences = new HashMap<>();
        // Cache markdown references for each file
        for (WebNode node : nodes.values()) {

            List<MarkdownLink> referencedLinks = new MarkdownLinkExtracter()
                    .findAllReferencedNodes(node.getFile());

            nodeReferences.put(node, referencedLinks);

            referencedLinks.forEach(link -> this.titles.put(link.getHref(), link.getText()));
        }

        walkThroughNodePairs(nodes, nodeReferences, (referrer, referree, relation) -> {

            if (relation == PathHelpers.PathRelation.ParentOf) {

                referrer.references(referree);

                referree.sitedBy(referrer);
            }
        });

        walkThroughNodePairs(nodes, nodeReferences, (referrer, referree, relation) -> {

            if (relation == PathHelpers.PathRelation.Sibling) {

                WebNode base = null;
                WebNode moving = null;

                if (!referrer.isSited() && referree.isSited()) {

                    base = referree;

                    moving = referrer;
                }
                if (referrer.isSited() && !referree.isSited()) {

                    base = referrer;

                    moving = referree;
                }

                if (base != null && moving != null) {

                    base.references(moving);

                    moving.sitedBy(base);
                }
            }
        });

    }

    private String filePathToLink(File file) {

        String refKey = this.baseDirectory.toPath()
                .relativize(file.toPath())
                .toString();

        if (refKey.toLowerCase().endsWith(".md")) {
            refKey = refKey.substring(0, refKey.length() - 3);
        }
        return refKey;
    }

    @Override
    public String getTextFor(String link) {

        if (this.titles.containsKey(link)) {

            return this.titles.get(link);
        }
        return null;
    }

    @Override
    public int getTotalNodesCount() {

        return this.keys.size();
    }

}
