/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.anchorsources;

import com.acidmanic.cicdassistant.utility.AnchorCache;
import com.acidmanic.cicdassistant.wiki.convert.autolink.Anchor;
import com.acidmanic.delegates.arg1.Function;
import com.acidmanic.lightweight.logger.Logger;

/**
 *
 * @author diego
 */
class KeyNormalizingDataFetchAnchorCache extends AnchorCache {

    private Function<String, String> normalize;

    public KeyNormalizingDataFetchAnchorCache(
            Function<Anchor, String> fetchData,
            Function<String, String> normalize,
            String cacheName,
            Logger logger) {

        super((link) -> fetchData.perform(normalize.perform(link)), cacheName, logger);

        this.normalize = normalize;

    }

    public KeyNormalizingDataFetchAnchorCache(
            Function<Anchor, String> fetchData,
            String cacheName,
            Logger logger) {

        this(fetchData, s -> s, cacheName, logger);
    }

    @Override
    public Anchor get(String link) {

        String normalizedLink = normalize.perform(link);

        return super.get(normalizedLink);
    }

}
