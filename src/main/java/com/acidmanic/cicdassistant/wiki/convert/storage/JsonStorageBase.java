/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.wiki.convert.storage;

import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.lightweight.logger.SilentLogger;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

/**
 *
 * @author diego
 * @param <T> model type
 */
public abstract class JsonStorageBase<T> {

    private final File dataFile;
    private final Logger logger;
    private final Class<T> modelType;

    public JsonStorageBase(File dataFile, Class<T> modelType, Logger logger) {
        this.dataFile = dataFile;
        this.logger = logger;
        this.modelType = modelType;
    }

    public JsonStorageBase(File dataFile, Class<T> modelType) {
        this.dataFile = dataFile;
        this.logger = new SilentLogger();
        this.modelType = modelType;
    }

    protected boolean save(T model) {
        if (this.dataFile.exists()) {
            this.dataFile.delete();
        }

        ObjectMapper mapper = createmapper();

        try {
            mapper.writeValue(this.dataFile, model);
            return true;
        } catch (Exception e) {
            this.logger.error(
                    this.getClass().getSimpleName() + ": "
                    + "Unable to save: " + e.getClass().getSimpleName());
        }
        return false;
    }

    protected T load() {
        T result = null;

        if (this.dataFile.exists()) {
            try {
                ObjectMapper mapper = createmapper();

                result = mapper.readValue(this.dataFile, modelType);

                return result;

            } catch (Exception e) {
                this.logger.error(
                        this.getClass().getSimpleName() + ": "
                        + "Unable to load: " + e.getClass().getSimpleName());
            }
        }
        return result;
    }

    private ObjectMapper createmapper() {
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY, false);
        mapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper;
    }

    protected Logger getLogger() {
        return this.logger;
    }

    protected File getXmlFile() {
        return this.dataFile;
    }
}
