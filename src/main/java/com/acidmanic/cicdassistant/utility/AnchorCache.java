/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.utility;

import com.acidmanic.delegates.arg1.Function;
import com.acidmanic.lightweight.jsonstorage.JsonStorageBase;
import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.lightweight.logger.SilentLogger;
import com.acidmanic.wiki.convert.autolink.Anchor;
import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

/**
 *
 * @author diego
 */
public abstract class AnchorCache {

    public static class CacheModel extends HashMap<String, Anchor> {
    };

    private class Storage extends JsonStorageBase<CacheModel> {

        public Storage(File dataFile, Logger logger) {
            super(dataFile, CacheModel.class, logger);
        }

        CacheModel read() {
            return super.load();
        }

        boolean write(CacheModel data) {
            return super.save(data);
        }
    }

    private final Function<Anchor, String> dataProvider;
    private CacheModel cachedData;
    private final Logger logger;
    private final File dataFile;
    private final String cacheName;

    public AnchorCache(Function<Anchor, String> dataProvider, String cacheName, Logger logger) {
        this.cacheName = cacheName;
        this.dataProvider = dataProvider;
        this.logger = new SilentLogger();
        this.dataFile = generateCacheFileDirectory();
        this.cachedData = loadModel();

    }

    public AnchorCache(Function<Anchor, String> dataProvider, String cacheName) {
        this(dataProvider, cacheName, new SilentLogger());
    }

    public Anchor get(String key) {

        Anchor value;

        if (!this.cachedData.containsKey(key)) {

            value = this.dataProvider.perform(key);

            if (value != null) {

                this.cachedData.put(key, value);

                saveChanges();
            }
        } else {
            value = this.cachedData.get(key);
        }
        return value;

    }

    private File generateCacheFileDirectory() {

        Path directory = new ResourceHelper().getExecutionDirectory()
                .resolve("Cache");

        if (!directory.toFile().exists()) {

            directory.toFile().mkdirs();
        }

        return directory.resolve(this.cacheName + ".json").toFile();
    }

    private CacheModel loadModel() {

        Storage storage = new Storage(this.dataFile, this.logger);

        CacheModel model = storage.read();

        if (model == null) {

            model = new CacheModel();

            storage.write(model);
        }

        return model;
    }

    private void saveChanges() {

        Storage storage = new Storage(this.dataFile, this.logger);

        storage.write(this.cachedData);
    }

}
