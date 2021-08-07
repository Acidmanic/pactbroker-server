/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.html.interception;

import com.acidmanic.cicdassistant.utility.PathHelpers;
import com.acidmanic.cicdassistant.utility.StringUtils;
import com.sun.javafx.scene.shape.PathUtils;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class is use full when your presenting an html on a sub-route of a
 * website.
 *
 * @author diego
 */
public class RebaseAnchorsHtmlInterceptor extends AnchorInterceptor {

    private final Path routeBase;

    public RebaseAnchorsHtmlInterceptor(Path routeBase) {
        this.routeBase = routeBase;
    }

    @Override
    protected String manipulateAnchor(String href) {

        URI uri = URI.create(href);

        if (uri.isAbsolute()) {
            return href;
        }
        Path target = Paths.get(href);

        if (target.isAbsolute()) {

            href = StringUtils.stripSides(href, "/");

            href = this.routeBase.resolve(href).normalize().toString();

            if (!routeBase.isAbsolute()) {
                
                href = "/" + href;
            }
        }
        return href;
    }

}
