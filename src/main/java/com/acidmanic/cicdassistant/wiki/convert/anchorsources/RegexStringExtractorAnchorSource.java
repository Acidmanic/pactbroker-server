/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.anchorsources;

import com.acidmanic.cicdassistant.utility.StringUtils;
import com.acidmanic.wiki.convert.anchorsources.DataExtractAnchorSourceBase;
import com.acidmanic.wiki.convert.autolink.Anchor;
import java.util.List;

/**
 *
 * @author diego
 */
public abstract class RegexStringExtractorAnchorSource extends DataExtractAnchorSourceBase {


    private final String regex;

    public RegexStringExtractorAnchorSource() {

        this.regex = getRegEx();
    }

    @Override
    protected Anchor createAnchor(String link) {

        Anchor anchor = new Anchor();

        anchor.setHref(link);

        anchor.setText(link);

        anchor.setTitle(link);

        return anchor;
    }

    @Override
    protected List<String> extractLinkData(String input) {

        return StringUtils.extractPatterns(input, this.regex);
    }

    protected abstract String getRegEx();

}
