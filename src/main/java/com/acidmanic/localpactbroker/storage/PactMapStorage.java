/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.storage;

import com.acidmanic.lightweight.jsonstorage.JsonStorageBase;
import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.localpactbroker.models.PactMap;
import com.acidmanic.localpactbroker.utility.Result;

/**
 *
 * @author diego
 */
public class PactMapStorage extends JsonStorageBase<PactMap>{
    
    public PactMapStorage(StorageFileConfigs fileConfigs, Logger logger) {
        super(fileConfigs.getPactMapFileStorage(), PactMap.class, logger);
    }
    
    
    public boolean write(PactMap model) {

        return super.save(model);
    }

    public Result<PactMap> read() {
        
        PactMap model = super.load();

        if (model != null) {

            return new Result<>(true, model);
        }
        return new Result<>();
    }
    
}
