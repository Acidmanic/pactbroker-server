/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.storage;

import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.localpactbroker.jsonstorage.JsonStorageBase;
import com.acidmanic.localpactbroker.models.pact.Token;
import java.io.File;

/**
 *
 * @author diego
 */
public class TokenStorage extends JsonStorageBase<Token> {

    public TokenStorage(File xmlFile, Logger logger) {
        super(xmlFile, Token.class, logger);
    }

}
