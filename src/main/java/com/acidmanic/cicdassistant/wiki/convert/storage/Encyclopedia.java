/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.storage;

import com.acidmanic.cicdassistant.wiki.convert.anchorsources.EncyclopediaEntry;
import java.util.HashMap;

/**
 *
 * @author diego
 */
public class Encyclopedia {

    private HashMap<String, EncyclopediaEntry> entries = new HashMap<>();

    public HashMap<String, EncyclopediaEntry> getEntries() {
        return entries;
    }

    public void setEntries(HashMap<String, EncyclopediaEntry> entries) {
        this.entries = entries;
    }

}
