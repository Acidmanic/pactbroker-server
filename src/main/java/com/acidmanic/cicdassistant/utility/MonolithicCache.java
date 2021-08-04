/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.utility;

import com.acidmanic.delegates.Function;
import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.lightweight.logger.SilentLogger;
import com.acidmanic.wiki.convert.storage.JsonStorageBase;
import java.io.File;
import java.nio.file.Path;

/**
 *
 * @author diego
 * @param <TData> Type of data to be cached
 */
public abstract class MonolithicCache<TData> {

    private class Storage extends JsonStorageBase<TData> {

        public Storage(File dataFile, Class modelType, Logger logger) {
            super(dataFile, modelType, logger);
        }

        TData read() {
            return super.load();
        }

        boolean write(TData data) {
            return super.save(data);
        }
    }

    protected abstract Class<? extends TData> getModelType();

    private final Function<TData> dataProvider;
    private TData cachedData;
    private final Logger logger;
    private final File dataFile;
    private final Class<? extends TData> modelType;

    public MonolithicCache(Function<TData> dataProvider, Logger logger) {
        this.dataProvider = dataProvider;
        this.logger = new SilentLogger();
        this.dataFile = generateCacheFileDirectory();
        this.modelType = getModelType();
    }

    public MonolithicCache(Function<TData> dataProvider) {
        this(dataProvider, new SilentLogger());
    }

    public final void update() {

        Storage storage = new Storage(this.dataFile, this.modelType, this.logger);

        TData data = storage.read();

        if (data == null) {

            data = this.dataProvider.perform();

            if (data != null) {

                this.cachedData = data;

                storage.write(data);
            }
        }
    }

    public TData get() {
        return cachedData;
    }

    private File generateCacheFileDirectory() {

        Class type = getModelType();

        String name = type.getSimpleName();

        Path directory = new ResourceHelper().getExecutionDirectory()
                .resolve("Cache");

        if (!directory.toFile().exists()) {

            directory.toFile().mkdirs();
        }

        return directory.resolve(name).toFile();
    }

}
