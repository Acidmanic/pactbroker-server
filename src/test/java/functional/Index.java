/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functional;

import com.acidmanic.cicdassistant.utility.MarkdownCleanup;
import com.acidmanic.cicdassistant.utility.PathHelpers;
import com.acidmanic.cicdassistant.utility.StringUtils;
import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author diego
 */
public class Index {

    private final MutableDataSet options = new MutableDataSet();

    private final File baseDirectory;

    private final HashMap<String, KeyNode> keys;

    private void printDown(KeyNode key, String indent) {

        System.out.println(indent + key.key );

        for (KeyNode child : key.references) {

            printDown(child, indent + "|------");
        }
    }

    private class KeyNode {

        public List<KeyNode> sitations = new ArrayList<>();
        public List<KeyNode> references = new ArrayList<>();

        public final String key;
        public final File file;

        public KeyNode(String key, File file) {
            this.key = key;
            this.file = file;
        }

        public void sitedBy(KeyNode key) {

            if (!this.sitations.contains(key)) {
                this.sitations.add(key);
            }
        }

        public void references(KeyNode key) {

            if (!this.references.contains(key)) {
                this.references.add(key);
            }
        }

        public boolean isSingular() {

            return this.sitations.isEmpty() && this.references.isEmpty();
        }

        public boolean isLeaf() {

            return !this.sitations.isEmpty() && this.references.isEmpty();
        }

        public boolean isHead() {

            return this.sitations.isEmpty() && !this.references.isEmpty();
        }

        public boolean isMidway() {

            return !this.sitations.isEmpty() && !this.references.isEmpty();
        }

        public boolean isSitedBy(KeyNode node) {

            for (KeyNode parent : this.sitations) {
                if (node.key == parent.key) {
                    return true;
                }
            }
            return false;
        }

        public boolean hasReferenced(KeyNode node) {

            for (KeyNode child : this.references) {
                if (node.key == child.key) {
                    return true;
                }
            }
            return false;
        }

    }

    public Index(File baseDirectory) {
        this.baseDirectory = baseDirectory;

        this.keys = new HashMap<>();

    }

    public void indexDirectory() {

        // Scan all files, and create nodes
        indexDirectory(baseDirectory);
        linkNodes(this.keys);

        System.out.println("Total Shsst count: " + this.keys.size());

        for (KeyNode key : keys.values()) {

            if (key.isHead()) {

                System.out.println("===================");
                printDown(key, "");
            }
        }

        System.out.println("===================");
        System.out.println("Misc");
        System.out.println("===================");

        for (KeyNode key : keys.values()) {

            if (key.isSingular()) {

                printDown(key, "");
            }
        }

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

                    this.keys.put(refKey, new KeyNode(refKey, file));
                }

            }
        }
    }

    private void linkNodes(HashMap<String, KeyNode> nodes) {

        for (KeyNode node : nodes.values()) {

            // Read the node's file
            // find other referenced nodes based on file system relation
            String markdown = readFile(node.file);

            List<String> referencedLinks = findAllReferencedNodes(markdown);

            for (String referencedLink : referencedLinks) {

                if (nodes.containsKey(referencedLink)) {

                    KeyNode referencedNode = nodes.get(referencedLink);

                    PathHelpers.PathRelation relation = new PathHelpers()
                            .relationOfRelativePaths(node.file.toPath(),
                                    referencedNode.file.toPath());

                    if (relation == PathHelpers.PathRelation.ParentOf) {

                        node.references(referencedNode);

                        referencedNode.sitedBy(node);
                    }
                    if (relation == PathHelpers.PathRelation.Sibling) {

                        if (!node.isSitedBy(referencedNode)
                                && !referencedNode.hasReferenced(node)) {

                            node.references(referencedNode);

                            referencedNode.sitedBy(node);
                        }
                    }
                }

//                Path linkedFile = findFileOf(referencedLink);
//
//                if (linkedFile != null) {
//                       
//                    PathHelpers.PathRelation relation = new PathHelpers()
//                            .relationOfRelativePaths(node.file.toPath(), linkedFile);
//                    
//                    if(relation == PathHelpers.PathRelation.ParentOf){
//                        
//                        
//                    }
//                }
            }

        }
    }

    private List<String> findAllReferencedNodes(String markdown) {

        List<String> links = new ArrayList();

        if (!StringUtils.isNullOrEmpty(markdown)) {

            markdown = new MarkdownCleanup().clean(markdown);

            Parser parser = Parser.builder(options).build();

            Node document = parser.parse(markdown);

            scan(document, links);
        }

        return links;
    }

    private String readFile(File file) {

        try {

            byte[] data = Files.readAllBytes(file.toPath());

            return new String(data);

        } catch (Exception e) {
        }

        return null;
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

    private Path findFileOf(String href) {

        File hrefFile = this.baseDirectory.toPath()
                .resolve(Paths.get(href))
                .normalize().toFile();

        if (hrefFile.exists()) {
            return hrefFile.toPath();
        }

        File hrefParent = hrefFile.toPath().getParent().toFile();

        String hrefFileName = hrefFile.getName() + ".md";

        if (hrefParent.exists() && hrefParent.isDirectory()) {

            File[] files = hrefParent.listFiles();

            for (File file : files) {

                if (hrefFileName.equalsIgnoreCase(file.getName())) {

                    return file.toPath();
                }
            }
        }

        return null;

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

}
