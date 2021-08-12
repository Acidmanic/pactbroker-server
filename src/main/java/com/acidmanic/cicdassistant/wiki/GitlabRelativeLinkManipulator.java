/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki;

import com.acidmanic.cicdassistant.wiki.linkprocessing.LinkManipulator;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author diego
 */
public class GitlabRelativeLinkManipulator implements LinkManipulator{

    @Override
    public String manipulate(String link) {
        
        URI uri = URI.create(link);

        if (uri.isAbsolute()) {
            return link;
        }
        Path target = Paths.get(link);

        if (!target.isAbsolute()) {

            link = "/" + link;
        }
        return link;
    }
    
}
