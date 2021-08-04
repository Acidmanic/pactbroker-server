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
public class GenericCodeToHtml implements CodeToHtml{

    @Override
    public String getHtmlFor(String code) {
        
        return code;
    }

    @Override
    public boolean supports(String language) {
        return true;
    }
    
}
