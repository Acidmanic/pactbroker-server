/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.stylesheet;

import java.util.List;

/**
 *
 * @author diego
 */
public interface HtmlStyleProvider {
    
    List<String> getNames();
    
    String getHeadInjectableHtml(String name);
}
