/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.controllers;

import com.acidmanic.delegates.Function;
import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.localpactbroker.models.Dto;
import com.acidmanic.localpactbroker.storage.TokenStorage;
import com.acidmanic.localpactbroker.utility.Result;

/**
 *
 * @author diego
 */
public class ControllerBase {

    private final TokenStorage tokenStorage;
    private final Logger logger;

    public ControllerBase(TokenStorage tokenStorage, Logger logger) {
        this.tokenStorage = tokenStorage;
        this.logger = logger;
    }

    protected <T> Dto<T> authorize(String token, Function<Dto<T>> innerCode) {
        if (token != null) {
            Result<String> tokenResult = this.tokenStorage.read();
            if (tokenResult.isSuccessfull()) {
                if (tokenResult.getValue().equals(token)) {
                    return innerCode.perform();
                }
            }
        }
        this.logger.warning("Un authorized attempt to brocker api.");
        return Models.failureDto(HttpStatus.UNAUTHORIZED);
    }

}
