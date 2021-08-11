/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.utility;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author diego
 */
public class QueryStringMap {

    private final String queryString;

    private HashMap<String, String> queries;

    public QueryStringMap(String queryString) {
        this.queryString = queryString;

        queries = new HashMap<>();

        queryString = StringUtils.stripSides(queryString, "?", "");

        List<String> segments = StringUtils.split(queryString, "&", true);

        for (String segment : segments) {

            int st = segment.indexOf(":");

            if (st > -1) {

                String key = segment.substring(0, st);

                String value = "";

                if (st < segment.length()) {

                    value = segment.substring(st + 1, segment.length());
                }

                try {
                    key = URLDecoder.decode(key, "utf-8");
                } catch (UnsupportedEncodingException ex) {
                }
                try {
                    value = URLDecoder.decode(value, "utf-8");
                } catch (UnsupportedEncodingException ex) {
                }

                this.queries.put(key, value);
            }
        }
    }

    public String get(String key) {

        return this.queries.getOrDefault(key, null);
    }

    public String getOriginalQueryString() {
        return queryString;
    }
}
