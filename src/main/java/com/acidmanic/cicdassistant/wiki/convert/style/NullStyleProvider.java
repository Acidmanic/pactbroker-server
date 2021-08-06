/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.style;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author diego
 */
public class NullStyleProvider implements HtmlStyleProvider{

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        
        names.add("No Style");
        
        return names;
    }

    @Override
    public String getHeadInjectableHtml(String name) {
        return "<style></style>";
    }
    
}
