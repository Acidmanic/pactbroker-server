/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.storage;

import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.localpactbroker.jsonstorage.JsonStorageBase;
import com.acidmanic.localpactbroker.models.pact.Pact;

/**
 *
 * @author diego
 */
public class PactStorage extends JsonStorageBase<Pact>{
    
    public PactStorage(StorageFileConfigs fileConfigs, Logger logger) {
        super(fileConfigs.getPactFileStorage(), Pact.class, logger);
    }
    
}
