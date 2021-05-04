/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.application;

import com.acidmanic.localpactbroker.storage.StorageFileConfigs;
import com.acidmanic.localpactbroker.utility.ResourceHelper;
import java.io.File;

/**
 *
 * @author diego
 */
public class BrokerStorageConfigs implements StorageFileConfigs {

    private final File pacts;
    private final File token;
    private final File badges;

    public BrokerStorageConfigs() {
        this.pacts = new ResourceHelper().getExecutionDirectory()
                .resolve("PactFilesStorage.json").toFile();
        this.token = new ResourceHelper().getExecutionDirectory()
                .resolve("Token.json").toFile();
        this.badges = new ResourceHelper().getExecutionDirectory()
                .resolve("Badges.json").toFile();
    }

    @Override
    public File getPactFileStorage() {
        return this.pacts;
    }

    @Override
    public File getTokenFileStorage() {
        return this.token;
    }

    @Override
    public File getBadgesFileStorage() {
        return this.badges;
    }

}
