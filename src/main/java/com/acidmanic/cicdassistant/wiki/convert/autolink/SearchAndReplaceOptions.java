/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.autolink;

/**
 *
 * @author diego
 */
public class SearchAndReplaceOptions {

    public static final SearchAndReplaceOptions DEFAULT = new SearchAndReplaceOptions(false, true, false);

    private boolean caseSensitive;
    private boolean boundedWords;
    private boolean allowRepeating;

    public SearchAndReplaceOptions(boolean caseSensitive, boolean boundedWords, boolean allowRepeating) {
        this.caseSensitive = caseSensitive;
        this.boundedWords = boundedWords;
        this.allowRepeating = allowRepeating;
    }

    public SearchAndReplaceOptions() {
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public SearchAndReplaceOptions setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
        return this;
    }

    public boolean isBoundedWords() {
        return boundedWords;
    }

    public SearchAndReplaceOptions setBoundedWords(boolean boundedWords) {
        this.boundedWords = boundedWords;
        return this;
    }

    public boolean isAllowRepeating() {
        return allowRepeating;
    }

    public SearchAndReplaceOptions setAllowRepeating(boolean allowRepeating) {
        this.allowRepeating = allowRepeating;
        return this;
    }

}
