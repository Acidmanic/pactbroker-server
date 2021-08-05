/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.autolink;

import com.acidmanic.cicdassistant.utility.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author diego
 */
public class SearchAndReplaceText {

    private final String originalText;
    private final String searchText;
    private final List<Mark> searchables;
    private final List<Mark> toBeReplaced;

    private final boolean caseSensitive;
    private final boolean boundedWord;
    private final boolean allowRepeating;

    private final List<String> alreadyMarked;

    public SearchAndReplaceText(String text, SearchAndReplaceOptions options) {
        this.originalText = text;
        this.searchables = new ArrayList<>();
        this.toBeReplaced = new ArrayList<>();
        Mark mark = new Mark(0, text.length());
        this.searchables.add(mark);
        this.caseSensitive = options.isCaseSensitive();
        this.searchText = caseSensitive ? text : text.toLowerCase();
        this.boundedWord = options.isBoundedWords();
        this.alreadyMarked = new ArrayList<>();
        this.allowRepeating = options.isAllowRepeating();
    }

    public SearchAndReplaceText(String text, boolean caseSensitive) {
        this(text, new SearchAndReplaceOptions(caseSensitive, true, false));
    }

    /**
     * This method finds any occurrences of the find string. and marks them to
     * be replaced.
     *
     * @param find
     * @param replaceString
     * @return the number of marked occurrences
     */
    public int searchAndMarkForReplacement(String find, String replaceString) {

        boolean dirty = true;

        int occurrences = 0;

        while (dirty) {

            dirty = false;

            if (this.allowRepeating || !isAlreadyMarkedForReplacement(find)) {

                Mark mark = indexOf(find);

                if (mark != null) {

                    this.alreadyMarked.add(find);

                    occurrences += 1;

                    dirty = true;

                    replace(mark, find, replaceString);
                }
            }
        }
        return occurrences;
    }

    private Mark indexOf(String find) {

        String findString = this.caseSensitive ? find : find.toLowerCase();

        for (Mark mark : this.searchables) {

            String chunk = mark.pickChunk(this.searchText);

            if (contains(chunk, findString)) {

                return mark;
            }
        }
        return null;
    }

    private void replace(Mark mark, String find, String replaceString) {

        String findString = this.caseSensitive ? find : find.toLowerCase();

        String chunk = mark.pickChunk(this.searchText);

        int st = chunk.indexOf(findString, 0);

        int offset = mark.getStart();

        if (st > -1) {

            int nd = st + findString.length();

            Mark found = new Mark(st, nd, replaceString).offset(offset);
            Mark preFound = new Mark(0, st).offset(offset);
            Mark postFound = new Mark(nd, chunk.length()).offset(offset);

            this.toBeReplaced.add(found);

            this.searchables.add(preFound);
            this.searchables.add(postFound);

            this.searchables.remove(mark);

        }
    }

    public String replace() {

        List<Mark> marks = new ArrayList<>();

        marks.addAll(this.searchables);
        marks.addAll(this.toBeReplaced);

        marks.sort((m1, m2) -> m1.getStart() - m2.getStart());

        StringBuilder builder = new StringBuilder();

        for (Mark mark : marks) {

            if (mark.isTagged()) { //tobe replaced

                builder.append(mark.getTag());
            } else {
                builder.append(mark.pickChunk(this.originalText));
            }
        }
        return builder.toString();
    }

    private boolean contains(String chunk, String find) {

        if (chunk == null || chunk.length() == 0) {
            return false;
        }

        int st = chunk.indexOf(find);

        if (st > -1) {

            if (!boundedWord) {

                return true;
            }
            // it contains and we need it to be bounded
            int nd = st + find.length();

            return isBoundIndex(chunk, st - 1) && isBoundIndex(chunk, nd);

        }
        return false;
    }

    private boolean isAlreadyMarkedForReplacement(String term) {

        if (StringUtils.isNullOrEmpty(term)) {
            return true;
        }

        for (String item : this.alreadyMarked) {

            if ((this.caseSensitive && term.equals(item))
                    || (!this.caseSensitive && term.equalsIgnoreCase(item))) {
                return true;
            }
        }
        return false;
    }

    private boolean isBoundIndex(String chunk, int i) {

        if (i < 0 || i >= chunk.length()) {

            return true;
        }

        char boundChar = chunk.charAt(i);

        return Character.isWhitespace(boundChar)
                || boundChar == '.'
                || boundChar == ','
                || boundChar == '"'
                || boundChar == '\''
                || boundChar == '`'
                || boundChar == ';';
    }

}
