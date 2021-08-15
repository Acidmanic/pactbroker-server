/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki;

import com.acidmanic.cicdassistant.http.Router;
import com.acidmanic.cicdassistant.wiki.linkprocessing.LinkManipulator;
import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author diego
 */
public class GitlabRelativeLinkManipulator implements LinkManipulator {

    private final Router wikiRouter;

    public GitlabRelativeLinkManipulator(Router wikiRouter) {
        this.wikiRouter = wikiRouter;
    }

    @Override
    public String manipulate(String link) {

        URI uri = URI.create(link);

        if (uri.isAbsolute()) {
            return link;
        }
        Path target = Paths.get(link);

        if (!target.isAbsolute() && isAMarkDownFile(link)) {

            link = "/" + link;
        }
        return link;
    }

    private boolean isAMarkDownFile(String link) {

        File target = this.wikiRouter.mapPath(link);

        return target != null
                && target.exists()
                && target.getName().toLowerCase().endsWith(".md");
    }

}
