/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.infrastructure.configuration;

import com.acidmanic.cicdassistant.utility.ResourceHelper;
import com.acidmanic.lightweight.jsonstorage.JsonStorageBase;
import java.io.File;

/**
 *
 * @author diego
 */
public abstract class ConfigurationBuilder<T> {

    private class ConfigurationStorage<T> extends JsonStorageBase<T> {

        public ConfigurationStorage(File xmlFile, Class<T> modelType) {
            super(xmlFile, modelType);
        }

        public T read() {

            try {
                return super.load();

            } catch (Exception e) {
            }
            return null;
        }

        public void write(T configurations) {

            super.save(configurations);
        }

    }

    private final T defaults;
    private final ConfigurationStorage<T> storage;

    protected ConfigurationBuilder() {

        this.defaults = createDefaults();

        this.storage = makeStorage((Class<T>) this.defaults.getClass());
    }

    private ConfigurationStorage<T> makeStorage(Class<T> modelType) {

        File jsonFile = new ResourceHelper().getExecutionDirectory()
                .resolve(modelType.getSimpleName() + ".json").toFile();

        return new ConfigurationStorage<>(jsonFile, modelType);
    }

    public T readConfigurations() {

        T configurations = this.storage.read();

        if (configurations == null) {

            configurations = this.defaults;

            this.storage.write(configurations);
        }
        return configurations;
    }

    public void writeConfigurations(T configurations) {

        this.storage.write(configurations);
    }

    protected abstract T createDefaults();
}
