/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.wiki.convert.autolink;

import java.util.HashMap;

/**
 *
 * @author diego
 */
public class AnchorReplacementMap extends HashMap<SearchTarget, Anchor> {

    public boolean containsTerm(String term) {

        for (SearchTarget target : this.keySet()) {

            if (target.isCaseSensitive()) {

                if (target.getTerm().equals(term)) {
                    return true;
                }
            } else {
                if (target.getTerm().equalsIgnoreCase(term)) {
                    return true;
                }
            }
        }
        return false;
    }
}
