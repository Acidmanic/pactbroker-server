/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.autolink;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author diego
 */
public class AnchorMarker {

    public static final String TAG_ANCHOR = "ANCHOR";
    public static final String TAG_NORMAL = "NORMAL";

    public List<Mark> splitHtmlByAnchors(String html) {

        final int READY = 0;
        final int TRI = 1;
        final int ANCHOR = 2;
        final int SLASH = 3;

        List<Mark> marks = new ArrayList<>();

        int startIndex = 0;
        int triIndex = 0;

        int state = READY;

        char[] chars = html.toCharArray();

        for (int i = 0; i < chars.length; i++) {

            char c = chars[i];

            if (state == READY) {

                if (c == '<') {
                    state = TRI;
                    triIndex = i;
                }
            } else if (state == TRI) {

                switch (c) {
                    case 'a':
                    case 'A':
                        //deliver normal
                        Mark mark = new Mark(startIndex, i + 1, TAG_NORMAL);
                        marks.add(mark);
                        state = ANCHOR;
                        startIndex = triIndex;
                        break;
                    case '<':
                        triIndex = i;
                        break;
                    default:
                        state = READY;
                        break;
                }
            } else if (state == ANCHOR) {

                if (c == '/') {
                    state = SLASH;
                }
            } else if (state == SLASH) {

                if (c == '>') {
                    //deliver anchor
                    Mark mark = new Mark(startIndex, i + 1, TAG_ANCHOR);
                    marks.add(mark);

                    state = READY;

                    startIndex = i + 1;
                }
            }
        }

        if (startIndex < chars.length) {

            Mark mark = new Mark(startIndex, chars.length, state == ANCHOR ? TAG_ANCHOR : TAG_NORMAL);
            marks.add(mark);
        }

        return marks;
    }
}
