/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.autolink;

import com.acidmanic.cicdassistant.utility.StringUtils;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * This class will tell you whether or not you're inside a tag from the given
 * list of tags. It takes a list of tags to watch on its constructor. then while
 * your doing your processing, whenever you hit an HTML tag, you would call the
 * method <code>count</code>, passing the tag string. (it can include
 * triangles). At any point you can use the method <code>inZone</code> to see if
 * are you currently inside one of the given tags or not.
 *
 * <p>
 * Given list of tags does not include triangles. Just tag names</p>
 * <p>
 * Tag names are case-insensitive</p>
 *
 * @author diego
 */
public class TagTracer {

    private final HashMap<String, Integer> occuranceCount = new HashMap<>();

    public TagTracer(String... tags) {

        addTagNames(tags);
    }

    public TagTracer(Collection<String> tags) {

        addTagNames(tags);
    }

    public TagTracer() {

    }

    public TagTracer addTagNames(Collection<String> tags) {

        tags.forEach((tag) -> this.occuranceCount.put(tag, 0));

        return this;
    }

    public TagTracer addTagNames(String... tags) {

        for (String tag : tags) {

            this.occuranceCount.put(tag, 0);
        }
        return this;
    }

    public void count(String tagString) {

        String tagHead = tagString.toLowerCase().trim();

        tagHead = StringUtils.stripSides(tagHead, "<", ">");

        int addSign = 1;

        if (tagHead.startsWith("/")) {
            addSign = -1;
            tagHead = tagHead.substring(1, tagHead.length());
        }

        List<String> segments = StringUtils.split(tagHead, "\\s", true);

        if (!segments.isEmpty()) {

            String tagName = segments.get(0);

            if (isATagOfInterest(tagName)) {

                int currentCount = 0;

                if (occuranceCount.containsKey(tagName)) {

                    currentCount = occuranceCount.get(tagName);

                    occuranceCount.remove(tagName);
                }
                currentCount += addSign;

                occuranceCount.put(tagName, currentCount);
            }
        }

    }

    public boolean inZone() {

        for (int count : occuranceCount.values()) {
            // if odd number 
            if (count % 2 == 1) {
                return true;
            }
        }
        return false;
    }

    private boolean isATagOfInterest(String tagName) {

        for (String pTag : this.occuranceCount.keySet()) {

            if (pTag.equalsIgnoreCase(tagName)) {
                return true;
            }
        }
        return false;
    }
    
    public String zone(){
        
        StringBuilder sb = new StringBuilder();
        
        for(String tagName:this.occuranceCount.keySet()){
            
            int count = this.occuranceCount.get(tagName);
            
            if(count % 2 ==1){
                
                sb.append(tagName).append(" - ");
            }
        }
        return sb.toString();
    }
}
