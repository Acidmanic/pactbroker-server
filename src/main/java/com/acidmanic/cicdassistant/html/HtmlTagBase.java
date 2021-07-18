/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.html;

import java.util.HashMap;

/**
 *
 * @author diego
 */
public abstract class HtmlTagBase implements Tag {

    protected String tagName() {

        return this.getClass().getSimpleName().toLowerCase();
    }

    protected abstract boolean isSingleTag();

    private final HashMap<String, String> attributes;

    public HtmlTagBase() {

        this.attributes = new HashMap<>();

    }

    protected void setAttribute(String name, String value) {

        if (this.attributes.containsKey(name)) {

            this.attributes.remove(value);
        }
        this.attributes.put(name, value);
    }

    protected String getAttribute(String name) {

        if (this.attributes.containsKey(name)) {

            return this.attributes.get(name);
        }
        return null;
    }

    protected abstract StringBuilder appendContent(StringBuilder sb);

    @Override
    public StringBuilder append(StringBuilder sb) {

        sb.append("<").append(tagName());

        for (String attName : this.attributes.keySet()) {

            String attValue = this.attributes.get(attName);

            sb.append(" ").append(attName)
                    .append("=").append("\"")
                    .append(attValue).append("\"");
        }

        if (isSingleTag()) {
            sb.append("/>");
        } else {
            sb.append(">");

            this.appendContent(sb);

            sb.append("</").append(tagName()).append(">");
        }
        return sb;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        String content = this.append(sb).toString();

        return content;
    }

}
