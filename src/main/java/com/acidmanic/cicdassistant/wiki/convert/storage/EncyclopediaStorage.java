/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.storage;

import com.acidmanic.lightweight.jsonstorage.JsonStorageBase;
import com.acidmanic.lightweight.logger.Logger;
import java.io.File;

/**
 *
 * @author diego
 */
public class EncyclopediaStorage extends JsonStorageBase<Encyclopedia> {

    public EncyclopediaStorage(File dataFile, Logger logger) {

        super(dataFile, Encyclopedia.class, logger);
    }

    public Encyclopedia read() {

        Encyclopedia model = new Encyclopedia();

        try {

            model = super.load();
        } catch (Exception e) {
        }

        if (model == null) {
            model = new Encyclopedia();
        }

        return model;
    }

}
