/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.html;

import com.acidmanic.cicdassistant.utility.Result;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        Result<String> contains = containsAttributeKey(name);

        if (contains.isSuccessfull()) {

            this.attributes.remove(contains.getValue());
        }
        this.attributes.put(name, value);
    }

    protected String getAttribute(String name) {

        Result<String> contains = containsAttributeKey(name);

        if (contains.isSuccessfull()) {

            String foundKey = contains.getValue();

            String value = this.attributes.get(foundKey);

            return value;
        }
        return null;
    }

    protected Result<String> containsAttributeKey(String key) {

        for (String attKey : this.attributes.keySet()) {
            if (key.equalsIgnoreCase(attKey)) {

                return new Result<>(true, attKey);
            }
        }
        return new Result<>(false, "");
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

    public String getId() {

        return getAttribute("id");
    }

    public HtmlTagBase setId(String id) {

        setAttribute("id", id);

        return this;
    }

    public String getCssClass() {

        return getAttribute("class");
    }

    public HtmlTagBase setCssClass(String cssClass) {

        setAttribute("class", cssClass);

        return this;
    }

    private List<String> getCssClasses() {

        String classes = getAttribute("class");

        List<String> classesList = new ArrayList<>();

        if (classes == null) {

            return classesList;
        }

        String[] classesArray = classes.split("\\s");

        for (String className : classesArray) {

            if (className != null && !className.isEmpty()) {

                classesList.add(className);
            }
        }
        return classesList;
    }

    public HtmlTagBase addCssClass(String cssClass) {

        List<String> classes = getCssClasses();

        if (!classes.contains(cssClass)) {

            classes.add(cssClass);
        }

        putCssClasses(classes);

        return this;
    }

    public HtmlTagBase removeCssClass(String cssClass) {

        List<String> classes = getCssClasses();

        while (classes.contains(cssClass)) {

            classes.remove(cssClass);
        }

        putCssClasses(classes);

        return this;
    }

    private void putCssClasses(List<String> classes) {
        String cssClasses = "";
        String sep = "";

        for (String className : classes) {

            cssClasses += sep + className;
            sep = " ";
        }
        setCssClass(cssClasses);
    }

    public void copyAttributesInto(HtmlTagBase node) {

        node.attributes.clear();

        node.attributes.putAll(attributes);
    }

    public String getStyle() {

        return getAttribute("style");
    }

    public HtmlTagBase setStyle(String style) {

        setAttribute("style", style);

        return this;
    }
    
    protected void removeAttribute(String name){
        
        Result<String> attKey = containsAttributeKey(name);
        
        if(attKey.isSuccessfull()){
            
            this.attributes.remove(attKey.getValue());
        }
    }

    public <T> T as() {
        return (T) this;
    }
    
    
    
    
}
