/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.storage;

import com.acidmanic.lightweight.jsonstorage.JsonStorageBase;
import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.cicdassistant.utility.Result;
import com.acidmanic.pact.models.Pact;

/**
 *
 * @author diego
 */
public class PactStorage extends JsonStorageBase<Pact> {

    public PactStorage(StorageFileConfigs fileConfigs, Logger logger) {
        super(fileConfigs.getPactFileStorage(), Pact.class, logger);
    }

    public boolean write(Pact pact) {

        return super.save(pact);
    }

    public Result<Pact> read() {
        Pact model = super.load();

        if (model != null) {
            
            return new Result<>(true, model);
        }
        return new Result<>();
    }
}
