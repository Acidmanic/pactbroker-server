/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.html;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author diego
 */
public abstract class ContainerHtmlTagBase extends HtmlTagBase {
    
    @Override
    protected boolean isSingleTag() {
        return false;
    }

    private final List<Tag> children;

    public ContainerHtmlTagBase() {

        this.children = new ArrayList<>();
    }

    public ContainerHtmlTagBase addChild(Tag child) {

        if (isSingleTag()) {

            return this;
        }
        this.children.add(child);

        return this;
    }

    @Override
    protected StringBuilder appendContent(StringBuilder sb) {

        this.children.forEach(child -> child.append(sb));

        return sb;
    }

}
