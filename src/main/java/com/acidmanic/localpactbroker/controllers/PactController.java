/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.controllers;

import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.localpactbroker.application.services.web.Controller;
import com.acidmanic.localpactbroker.models.Dto;
import com.acidmanic.localpactbroker.models.Pact;
import com.acidmanic.localpactbroker.storage.PactStorage;
import com.acidmanic.localpactbroker.storage.TokenStorage;
import com.acidmanic.localpactbroker.utility.Result;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author diego
 */
@Controller
@Path("/")
public class PactController {

    private final PactStorage pactStorage;
    private final TokenStorage tokenStorage;
    private final Logger logger;

    public PactController(PactStorage pactStorage, TokenStorage tokenStorage, Logger logger) {
        this.pactStorage = pactStorage;
        this.tokenStorage = tokenStorage;
        this.logger = logger;
    }

    
    @GET
    @Path("/pull")
    @Produces("application/json")
    public Dto<Pact> pull(@HeaderParam("token") String token) {
        
        if(token!=null){
            
            Result<String> tokenResult = this.tokenStorage.read();
            
            if(tokenResult.isSuccessfull()){
                
                if(tokenResult.getValue().equals(token)){
                    
                    Result<Pact> pactResult = this.pactStorage.read();
                    
                    if(pactResult.isSuccessfull()){
                        return new Dto(pactResult.getValue());
                    }else{
                        return Models.failureDto(HttpStatus.NOT_FOUND);
                    }
                }else{
                    return Models.failureDto(HttpStatus.UNAUTHORIZED);
                }
            }else{
                return Models.failureDto(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            
        }
        return Models.failureDto(HttpStatus.UNAUTHORIZED);
    }
}
