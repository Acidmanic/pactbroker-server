/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.autolink;

import com.acidmanic.cicdassistant.utility.StringUtils;
import com.acidmanic.wiki.convert.autolink.Mark;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author diego
 */
public class HtmlTextMarker {

    public static final String MARK_TAG = "TAG";
    public static final String MARK_TEXT = "TEXT";
    public static final String MARK_CONTENT = "CONTENT";

    private static final String[] PROTECTED_TAGS = {"code", "head", "style", "script", 
        "area", "map", "param", "obj", "input", "textarea", "select", "option",
        "optgroup", "button", "label", "fieldset", "legend", "noscript",
        "a", "applet", "audio", "base", "basefont", "canvas", "cite", "data",
        "dd", "del", "embed", "font", "isindex", "link", "nav", "output", "picture",
        "progress", "samp", "source", "svg", "template", "time", "title", "track",
        "var", "video"};

    public List<Mark> markTexts(String html) {

        List<Mark> marks = new ArrayList<>();

        boolean inTag = html.startsWith("<");
        int startIndex = 0;
        int lastCharDelivered = 0;
        HashMap<String, Integer> bannedCount = new HashMap<>();

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

                        count(bannedCount, html, mark);

                        marks.add(mark);
                    }
                }
            } else {// inText

                if (c == '<') {

                    lastCharDelivered = i + 1;

                    Mark mark = new Mark(startIndex + 1, lastCharDelivered - 1, MARK_TEXT);

                    startIndex = i;

                    inTag = true;

                    if (!mark.isEmpty()) {

                        if (isBanned(bannedCount)) {
                            mark.setTag(MARK_CONTENT);
                        }
                        marks.add(mark);
                    }
                }
            }
        }

        if (lastCharDelivered < chars.length) {

            Mark mark = new Mark(lastCharDelivered,
                    chars.length,
                    inTag ? MARK_TAG : MARK_TEXT);

            marks.add(mark);
        }
        return marks;
    }

    private void count(HashMap<String, Integer> bannedCount, String html, Mark mark) {

        String tagHead = mark.pickChunk(html).toLowerCase().trim();
        //strip <> away
        tagHead = tagHead.substring(1, tagHead.length() - 1).trim();

        int addSign = 1;

        if (tagHead.startsWith("/")) {
            addSign = -1;
            tagHead = tagHead.substring(1, tagHead.length());
        }

        List<String> segments = StringUtils.split(tagHead, "\\s", true);

        if (!segments.isEmpty()) {

            String tagName = segments.get(0);

            if (isProtected(tagName)) {

                int currentCount = 0;

                if (bannedCount.containsKey(tagName)) {

                    currentCount = bannedCount.get(tagName);

                    bannedCount.remove(tagName);
                }
                currentCount += addSign;

                bannedCount.put(tagName, currentCount);
            }
        }

    }

    private boolean isBanned(HashMap<String, Integer> bannedCount) {

        for (int count : bannedCount.values()) {
            // if odd number 
            if (count % 2 == 1) {
                return true;
            }
        }
        return false;
    }

    private boolean isProtected(String tagName) {

        for (String pTag : PROTECTED_TAGS) {

            if (pTag.equalsIgnoreCase(tagName)) {
                return true;
            }
        }
        return false;
    }

}
