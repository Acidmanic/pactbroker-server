/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.anchorsources;

import com.acidmanic.cicdassistant.wiki.convert.autolink.Anchor;
import com.acidmanic.cicdassistant.wiki.convert.autolink.AnchorReplacementMap;
import com.acidmanic.cicdassistant.wiki.convert.autolink.AnchorSource;
import com.acidmanic.cicdassistant.wiki.convert.autolink.SearchTarget;
import java.util.List;

/**
 *
 * @author diego
 */
public abstract class DataExtractAnchorSourceBase implements AnchorSource {

    private AnchorReplacementMap anchors = new AnchorReplacementMap();

    public DataExtractAnchorSourceBase() {
    }

    @Override
    public AnchorReplacementMap getAnchorsReplacementMap() {

        return this.anchors;
    }

    protected Anchor createAnchor(String anchorData) {

        Anchor anchor = new Anchor();

        anchor.setHref(anchorData);

        anchor.setText(anchorData);

        anchor.setTitle(anchorData);

        return anchor;
    }

    @Override
    public void preProcessInputString(String input) {

        List<String> links = extractLinkData(input);

        this.anchors = new AnchorReplacementMap();

        for (String link : links) {

            if (!anchors.containsTerm(link)) {

                SearchTarget target = new SearchTarget();

                target.setCaseSensitive(false);

                target.setTerm(link);

                Anchor anchor = createAnchor(link);

                anchors.put(target, anchor);
            }
        }
    }

    protected abstract List<String> extractLinkData(String input);

}
