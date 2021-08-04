/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.services;

import com.acidmanic.cicdassistant.utility.ResourceHelper;
import com.acidmanic.cicdassistant.wiki.convert.storage.Encyclopedia;
import com.acidmanic.cicdassistant.wiki.convert.storage.EncyclopediaStorage;
import com.acidmanic.lightweight.logger.Logger;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author diego
 */
public class EncyclopediaStore {

    private final Path directory;
    private final Logger logger;

    private final List<Encyclopedia> availables;

    public EncyclopediaStore(Logger logger) {

        this.directory = new ResourceHelper().getExecutionDirectory()
                .resolve("knowledge-base")
                .toAbsolutePath()
                .normalize();

        if (!this.directory.toFile().exists()) {

            this.directory.toFile().mkdirs();
        }
        this.logger = logger;

        this.availables = loadAvailables();
    }

    public List<Encyclopedia> getAvailables() {

        return this.availables;
    }

    private List<Encyclopedia> loadAvailables() {

        File[] files = this.directory.toFile().listFiles();

        List<Encyclopedia> availableList = new ArrayList<>();

        for (File file : files) {

            EncyclopediaStorage storage = new EncyclopediaStorage(file, logger);

            Encyclopedia model = storage.read();

            if (model != null) {

                availableList.add(model);
            }
        }
        return availableList;
    }
}
