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
public interface AnchorSource {

    AnchorReplacementMap getAnchorsReplacementMap();
    
    void preProcessInputString(String input);
}
