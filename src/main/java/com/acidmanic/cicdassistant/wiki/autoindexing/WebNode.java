/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.autoindexing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author diego
 */
public class WebNode {

    private final List<WebNode> sitations = new ArrayList<>();
    private final List<WebNode> references = new ArrayList<>();
    private final String key;
    private final File file;

    public WebNode(String key, File file) {
        this.key = key;
        this.file = file;
    }

    public void sitedBy(WebNode key) {
        if (!this.sitations.contains(key)) {
            this.sitations.add(key);
        }
    }

    public void references(WebNode key) {
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

    public boolean isSited() {
        return !this.sitations.isEmpty();
    }

    public boolean isSitedBy(WebNode node) {
        for (WebNode parent : this.sitations) {
            if (node.key == null ? parent.key == null : node.key.equals(parent.key)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasReferenced(WebNode node) {
        for (WebNode child : this.references) {
            if (node.key == null ? child.key == null : node.key.equals(child.key)) {
                return true;
            }
        }
        return false;
    }

    public List<WebNode> getSitations() {
        return sitations;
    }

    public List<WebNode> getReferences() {
        return references;
    }

    public String getKey() {
        return key;
    }

    public File getFile() {
        return file;
    }

}
