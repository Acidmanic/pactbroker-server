/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.autolink;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author diego
 */
public class HtmlTextMarker {

    /**
     * Indicates an HTML start tag or end tag. from (including) &lt; character
     * to &gt; character
     */
    public static final String MARK_TAG = "TAG";
    /**
     * Indicates text content. The human readable parts of the HTML
     */
    public static final String MARK_TEXT_CONTENT = "TEXT";
    /**
     * Indicates HTML content. But these content belong to tags that are not
     * supposed to be directly read by user. These content are for browser
     * components to use.
     */
    public static final String MARK_NONTEXT_CONTENT = "CONTENT";
    /**
     * Indicates HTML content which depending on the usage of
     * <code>HtmlTextMarker</code>, are marked to be filtered out.
     */
    public static final String MARK_PROTECTED_CONTENT = "PROTECTED";

    private final List<String> protectedTags = new ArrayList<>();

    public HtmlTextMarker(String... protectedTags) {

        this.protectedTags.addAll(Arrays.asList(protectedTags));
    }

    public HtmlTextMarker(Collection<String> protectedTags) {

        this.protectedTags.addAll(protectedTags);
    }

    public List<Mark> markTexts(String html) {

        List<Mark> marks = new ArrayList<>();

        boolean inTag = html.startsWith("<");
        int startIndex = 0;
        int lastCharDelivered = 0;

        TagTracer nonTexuals = new TagTracer()
                .addTagNames(HtmlTagGroups.NONE_TEXTUALS)
                .addTagNames(HtmlTagGroups.NONE_PARAGRAPHIC_TEXTS);

        TagTracer protecteds = new TagTracer(this.protectedTags);

        char[] chars = html.toCharArray();

        for (int i = 0; i < chars.length; i++) {

            char c = chars[i];

            if (inTag) {

                if (c == '>') {

                    lastCharDelivered = i + 1;

                    Mark mark = new Mark(startIndex, lastCharDelivered, MARK_TAG);

                    startIndex = i;

                    inTag = false;

                    if (!mark.isEmpty()) {

                        String tagString = mark.pickChunk(html);

                        nonTexuals.count(tagString);
                        protecteds.count(tagString);

                        marks.add(mark);
                    }
                }
            } else {// inText

                if (c == '<') {

                    lastCharDelivered = i + 1;

                    Mark mark = new Mark(startIndex + 1, lastCharDelivered - 1, MARK_TEXT_CONTENT);

                    startIndex = i;

                    inTag = true;

                    if (!mark.isEmpty()) {

                        if (nonTexuals.inZone()) {
                            
                            mark.setTag(MARK_NONTEXT_CONTENT);
                        }
                        // let protecteds override nonTextuals
                        if (protecteds.inZone()) {
                            mark.setTag(MARK_PROTECTED_CONTENT);
                        }
                        marks.add(mark);
                    }
                }
            }
        }

        if (lastCharDelivered < chars.length) {

            Mark mark = new Mark(lastCharDelivered,
                    chars.length,
                    inTag ? MARK_TAG : MARK_TEXT_CONTENT);

            marks.add(mark);
        }
        return marks;
    }

}
