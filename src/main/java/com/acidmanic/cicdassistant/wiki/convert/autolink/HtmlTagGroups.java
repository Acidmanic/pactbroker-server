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
public final class HtmlTagGroups {

    private HtmlTagGroups() {
    }

    public static final String[] TITLES_CONTENTS = {
        "thead", "th", "h1", "h2", "h3", "h4", "h5", "h6"
    };
    public static final String[] NONE_TEXTUALS = {
        "code", "head", "style", "script",
        "area", "map", "param", "obj", "input", "select", "option",
        "optgroup", "button", "fieldset", "legend", "noscript",
        "a", "applet", "audio", "base", "basefont", "canvas", "cite", "data",
        "dd", "del", "embed", "font", "isindex", "nav", "output", "picture",
        "progress", "samp", "source", "svg", "template", "time", "title", "track",
        "var", "video"
    };
    public static final String[] NONE_PARAGRAPHIC_TEXTS = {
        "textarea", "label", "thead", "th", "h1", "h2", "h3", "h4", "h5", "h6"
    };

}
