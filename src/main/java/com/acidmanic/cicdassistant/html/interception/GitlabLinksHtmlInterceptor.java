/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.html.interception;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class is use full when your presenting an html on a sub-route of a
 * website.
 *
 * @author diego
 */
public class GitlabLinksHtmlInterceptor extends AnchorInterceptor {
    
    
    @Override
    protected String manipulateAnchor(String href) {

        URI uri = URI.create(href);

        if (uri.isAbsolute()) {
            return href;
        }
        Path target = Paths.get(href);

        if (!target.isAbsolute()) {

            href = "/" + href;
        }
        return href;
    }

}
