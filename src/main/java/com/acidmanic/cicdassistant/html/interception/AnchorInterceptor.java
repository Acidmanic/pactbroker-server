/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.html.interception;

import com.acidmanic.cicdassistant.utility.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author diego
 */
public class AnchorInterceptor implements HtmlInterceptor {

    @Override
    public void manipulate(Document document) {

        Elements anchors = document.select("a");

        for (Element anchor : anchors) {

            String href = anchor.attr("href");

            if (!StringUtils.isNullOrEmpty(href)) {

                href = manipulateAnchor(href);

                anchor.attr("href", href);
            }
        }
    }

    protected String manipulateAnchor(String href) {

        return href;
    }

}
