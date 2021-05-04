/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.controllers;

import com.acidmanic.delegates.Function;
import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.localpactbroker.application.services.web.Controller;
import com.acidmanic.localpactbroker.models.Dto;
import com.acidmanic.localpactbroker.storage.PactStorage;
import com.acidmanic.localpactbroker.storage.TokenStorage;
import com.acidmanic.localpactbroker.utility.Result;
import com.acidmanic.pact.models.Pact;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author diego
 */
@Controller
@Path("/")
public class PactController extends ControllerBase {

    private final PactStorage pactStorage;

    public PactController(PactStorage pactStorage, TokenStorage tokenStorage, Logger logger) {
        super(tokenStorage, logger);
        this.pactStorage = pactStorage;
    }

    @GET
    @Path("/pull")
    @Produces("application/json")
    public Dto<Pact> pull(@HeaderParam("token") String token) {

        Function<Dto<Pact>> innerCode = () -> {

            Result<Pact> pactResult = this.pactStorage.read();

            if (pactResult.isSuccessfull()) {
                return new Dto(pactResult.getValue());
            } else {
                return Models.failureDto(HttpStatus.NOT_FOUND);
            }
        };

        Dto<Pact> response = authorize(token, innerCode);

        return response;
    }

    /**
     *
     * @param token
     * @param pact
     * @return
     */
    @POST
    @Path("/push")
    @Produces("application/json")
    public Dto<String> push(@HeaderParam("token") String token, Pact pact) {

        Function<Dto<String>> innerCode = () -> {

            if (pact == null) {

                return Models.failureDto(HttpStatus.BAD_REQUEST);
            }
            boolean success = this.pactStorage.write(pact);

            if (success) {

                return Models.SuccessDto();
            } else {

                return Models.failureDto(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        };

        Dto<String> response = authorize(token, innerCode);

        return response;
    }

}
