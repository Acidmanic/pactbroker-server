/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.flexmark.extensions.codeparsing;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author diego
 */
public class CodeToHtmlFactory {

    private static final List<CodeToHtml> CODE_TO_HTMLS;

    static {
        CODE_TO_HTMLS = new ArrayList<>();

        CODE_TO_HTMLS.add(new JsonCodeToHtml());
    }

    public CodeToHtml make(String code) {

        String language = getLanguage(code);

        for (CodeToHtml codeToHtml : CODE_TO_HTMLS) {

            if (codeToHtml.supports(language)) {
                return codeToHtml;
            }
        }
        return new GenericCodeToHtml();
    }

    private String getLanguage(String code) {

        code = code.trim();

        if (code.startsWith("```")) {
            int st = 3;

            int nd = code.indexOf("\n");

            if (nd > st) {

                String language = code.substring(st, nd);

                return language;
            }
        }
        return "";
    }
}
