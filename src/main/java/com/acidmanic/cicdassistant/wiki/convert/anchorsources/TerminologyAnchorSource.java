/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.anchorsources;

import com.acidmanic.cicdassistant.utility.StringUtils;
import com.acidmanic.cicdassistant.wiki.convert.autolink.Anchor;
import com.acidmanic.cicdassistant.wiki.convert.autolink.AnchorReplacementMap;
import com.acidmanic.cicdassistant.wiki.convert.autolink.AnchorSource;
import com.acidmanic.cicdassistant.wiki.convert.autolink.SearchTarget;
import java.util.HashMap;

/**
 *
 * @author diego
 */
public class TerminologyAnchorSource implements AnchorSource {

    private final AnchorReplacementMap replacements;

    public TerminologyAnchorSource() {

        this.replacements = new AnchorReplacementMap();
    }

    public TerminologyAnchorSource addEncyclopedia(HashMap<String, EncyclopediaEntry> encyclopedia) {

        for (String term : encyclopedia.keySet()) {

            if (!this.replacements.containsTerm(term)) {

                SearchTarget target = new SearchTarget();

                target.setCaseSensitive(false);

                target.setTerm(term);

                Anchor anchor = new Anchor();

                EncyclopediaEntry entry = encyclopedia.get(term);

                anchor.setHref(entry.getWebLink());
                anchor.setText(term);
                anchor.setTitle(StringUtils.isNullOrEmpty(entry.getDescription())
                        ? entry.getWebLink() : entry.getDescription());

                this.replacements.put(target, anchor);
            }
        }
        return this;
    }

    @Override
    public AnchorReplacementMap getAnchorsReplacementMap() {

        return this.replacements;
    }

    @Override
    public void preProcessInputString(String input) {
    }
}
