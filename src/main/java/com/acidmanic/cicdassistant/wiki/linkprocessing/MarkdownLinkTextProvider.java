/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.linkprocessing;

/**
 *
 * @author diego
 */
public class MarkdownLinkTextProvider implements LinkTextProvider {

    @Override
    public String getTextFor(String link) {

        if (link.toLowerCase().endsWith(".md")) {
            link = link.substring(0, link.length() - 3);
        }

        char[] chars = link.toCharArray();

        StringBuilder sb = new StringBuilder();

        boolean lastWhiteSpace = true;

        for (char c : chars) {

            if (Character.isWhitespace(c)) {

                if (!lastWhiteSpace) {

                    sb.append(" ");
                }
                lastWhiteSpace = true;

            } else {

                if (Character.isAlphabetic(c) || Character.isDigit(c)) {

                    sb.append(lastWhiteSpace ? Character.toUpperCase(c) : Character.toLowerCase(c));
                }
                lastWhiteSpace = false;
            }
        }

        return sb.toString();
    }

}
