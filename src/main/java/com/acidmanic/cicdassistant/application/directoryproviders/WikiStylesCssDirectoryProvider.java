/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.application.directoryproviders;

import com.acidmanic.cicdassistant.utility.ResourceHelper;
import com.acidmanic.cicdassistant.wiki.convert.styleproviders.CssDirectoryProvider;
import java.io.File;

/**
 *
 * @author diego
 */
public class WikiStylesCssDirectoryProvider implements CssDirectoryProvider {

    @Override
    public File getCssDirectory() {

        File directory = new ResourceHelper().getExecutionDirectory()
                .resolve("wiki-styles").toAbsolutePath().normalize().toFile();

        if (!directory.exists()) {
            directory.mkdirs();
        }

        return directory;
    }

}
