/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.commands;

import com.acidmanic.commandline.commands.context.ExecutionContext;
import com.acidmanic.localpactbroker.storage.PactStorage;
import com.acidmanic.localpactbroker.storage.TokenStorage;

/**
 *
 * @author diego
 */
public class ApplicationContext implements ExecutionContext {

    private final TokenStorage tokenStorage;
    private final PactStorage pactStorage;

    public ApplicationContext(TokenStorage tokenStorage, PactStorage pactStorage) {
        this.tokenStorage = tokenStorage;
        this.pactStorage = pactStorage;
    }

    public TokenStorage getTokenStorage() {
        return tokenStorage;
    }

    public PactStorage getPactStorage() {
        return pactStorage;
    }

}
