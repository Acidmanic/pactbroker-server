/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.anchorsources;

import com.acidmanic.cicdassistant.utility.CommonRegExes;
import com.acidmanic.cicdassistant.utility.StringUtils;
import com.acidmanic.cicdassistant.utility.WebPageInfo;
import com.acidmanic.lightweight.logger.SilentLogger;
import com.acidmanic.wiki.convert.autolink.Anchor;

/**
 *
 * @author diego
 */
public class SmartWebLinkAnchorSource extends RegexStringExtractorAnchorSource {

    private final KeyNormalizingDataFetchAnchorCache cache;

    public SmartWebLinkAnchorSource() {

        this.cache = new KeyNormalizingDataFetchAnchorCache(
                link -> fetchLinkAsAnchor(link),
                link -> normalizeLink(link),
                "web-link-info-cache",
                new SilentLogger());
    }

    @Override
    protected Anchor createAnchor(String link) {

        return cache.get(link);
    }

    private Anchor fetchLinkAsAnchor(String link) {
        Anchor anchor = new Anchor();
        WebPageInfo pageInfo = new WebPageInfo();
        pageInfo.load(link);
        anchor.setHref(link);
        String title = pageInfo.getTitle();
        anchor.setText(title);
        String description = pageInfo.getDescription();
        anchor.setTitle(description);
        return anchor;
    }

    private static String normalizeLink(String link) {

        if (StringUtils.isNullOrEmpty(link)) {
            return "";
        }
        if (link.endsWith("/")) {
            link = link.substring(0, link.length() - 1);
        }
        link = link.toLowerCase();
        return link;
    }

    @Override
    protected String getRegEx() {

        return CommonRegExes.URL_REGEX;
    }

}
