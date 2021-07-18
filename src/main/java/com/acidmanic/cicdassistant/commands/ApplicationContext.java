/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.commands;

import com.acidmanic.commandline.commands.context.ExecutionContext;
import com.acidmanic.cicdassistant.storage.PactStorage;
import com.acidmanic.cicdassistant.storage.TokenStorage;
import com.acidmanic.cicdassistant.tokengenerate.TokenGenerator;

/**
 *
 * @author diego
 */
public class ApplicationContext implements ExecutionContext {

    private final TokenStorage tokenStorage;
    private final PactStorage pactStorage;
    private final TokenGenerator tokenGenerator;
    private final ApplicationSwitch applicationswitch;

    public ApplicationContext(TokenStorage tokenStorage, PactStorage pactStorage, TokenGenerator tokenGenerator, ApplicationSwitch applicationExecution) {
        this.tokenStorage = tokenStorage;
        this.pactStorage = pactStorage;
        this.tokenGenerator = tokenGenerator;
        this.applicationswitch = applicationExecution;
    }

    public TokenStorage getTokenStorage() {
        return tokenStorage;
    }

    public PactStorage getPactStorage() {
        return pactStorage;
    }

    public TokenGenerator getTokenGenerator() {
        return tokenGenerator;
    }

    public ApplicationSwitch getApplicationswitch() {
        return applicationswitch;
    }

}
