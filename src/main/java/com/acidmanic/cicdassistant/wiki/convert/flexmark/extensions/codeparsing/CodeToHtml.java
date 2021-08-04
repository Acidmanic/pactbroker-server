/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.flexmark.extensions.codeparsing;

/**
 *
 * @author diego
 */
public interface CodeToHtml {

    String getHtmlFor(String code);

    boolean supports(String language);
}
