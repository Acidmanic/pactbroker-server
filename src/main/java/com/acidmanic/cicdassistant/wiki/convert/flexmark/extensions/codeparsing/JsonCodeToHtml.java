/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.flexmark.extensions.codeparsing;

import com.acidmanic.cicdassistant.utility.TextReformater;
import com.acidmanic.wiki.convert.jsonparsing.ClearerJsonParserMachine;
import com.acidmanic.wiki.convert.jsonparsing.HtmlWrapperJsonParserMachine;

/**
 *
 * @author diego
 */
public class JsonCodeToHtml implements CodeToHtml {

    @Override
    public String getHtmlFor(String codeText) {

        codeText = stripCodeChar(codeText);

        String jsonHtml = reJson(codeText);

        jsonHtml = new CopyBoxWrapper().addCopyCodeBox(jsonHtml, codeText);

        return jsonHtml;

    }

    @Override
    public boolean supports(String language) {

        return "json".equalsIgnoreCase(language);
    }

    private String stripCodeChar(String json) {

        json = json.trim();

        if (json.startsWith("```")) {
            json = json.substring(3, json.length());
        }
        if (json.endsWith("```")) {
            json = json.substring(0, json.length() - 3);
        }
        if (json.toLowerCase().startsWith("json")) {
            json = json.substring(4, json.length());
        }
        return json;
    }

    public String reJson(String json) {

        json = new ClearerJsonParserMachine().parse(json);

        HtmlWrapperJsonParserMachine wrapper = new HtmlWrapperJsonParserMachine();

        wrapper.setRawValueClass("json-number");

        wrapper.setStringClass("json-string");

        wrapper.setKeyClass("json-key");

        json = wrapper.parse(json);

        json = new TextReformater().indentJson(json, true);

        return json;
    }

}
