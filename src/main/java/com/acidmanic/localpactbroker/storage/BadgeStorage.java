/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.storage;

import com.acidmanic.lightweight.jsonstorage.JsonStorageBase;
import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.localpactbroker.models.BadgeMap;
import com.acidmanic.localpactbroker.utility.Result;

/**
 *
 * @author diego
 */
public class BadgeStorage extends JsonStorageBase<BadgeMap> {

    public BadgeStorage(StorageFileConfigs fileConfigs, Logger logger) {
        super(fileConfigs.getBadgesFileStorage(), BadgeMap.class, logger);
    }

    public boolean write(BadgeMap badgeMap) {

        return super.save(badgeMap);
    }

    public Result<BadgeMap> read() {
        BadgeMap model = super.load();

        if (model != null) {

            return new Result<>(true, model);
        }
        return new Result<>();
    }

}
