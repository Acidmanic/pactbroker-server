/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.controllers;

import com.acidmanic.delegates.Function;
import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.cicdassistant.application.services.web.Controller;
import com.acidmanic.cicdassistant.models.Dto;
import com.acidmanic.cicdassistant.services.PactsManagerService;
import com.acidmanic.cicdassistant.storage.TokenStorage;
import com.acidmanic.pact.models.Pact;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author diego
 */
@Controller
@Path("broker")
public class PactController extends ControllerBase {

    private final PactsManagerService pactsManagerService;

    public PactController(PactsManagerService pactsManagerService, TokenStorage tokenStorage, Logger logger) {
        super(tokenStorage, logger);
        this.pactsManagerService = pactsManagerService;
    }

    @GET
    @Path("/pull")
    @Produces("application/json")
    public Dto<Pact> pull(@HeaderParam("token") String token) {

        Function<Dto<Pact>> innerCode = () -> {

            return this.pactsManagerService.pull();
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
    public Dto push(@HeaderParam("token") String token, Pact pact) {

        Function<Dto<String>> innerCode = () -> {

            return this.pactsManagerService.push(pact);

        };

        Dto response = authorize(token, innerCode);

        return response;
    }

    /**
     *
     * @param token
     * @param tag
     * @param pact
     * @return
     */
    @POST
    @Path("/store/{tag}")
    @Produces("application/json")
    public Dto<Object> store(@HeaderParam("token") String token,
            @PathParam("tag") String tag,
            Pact pact) {

        Function<Dto<Object>> innerCode = () -> {

            return this.pactsManagerService.store(tag, pact);
        };

        Dto<Object> response = authorize(token, innerCode);

        return response;
    }

    @GET
    @Path("/tags")
    @Produces("application/json")
    public Dto<List<String>> getTags(@HeaderParam("token") String token) {

        Function<Dto<List<String>>> innerCode = () -> {

            return pactsManagerService.getTags();
        };

        Dto<List<String>> response = authorize(token, innerCode);

        return response;
    }

    @PUT
    @Path("/elect/{tag}")
    @Produces("application/json")
    public Dto<Pact> elect(
            @HeaderParam("token") String token,
            @PathParam("tag") String tag) {

        Function<Dto<Pact>> innerCode = () -> {

            return pactsManagerService.elect(tag);
        };

        Dto<Pact> response = authorize(token, innerCode);

        return response;
    }

}
