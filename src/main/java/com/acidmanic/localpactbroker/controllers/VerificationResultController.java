/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.controllers;

import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.localpactbroker.application.services.web.Controller;
import com.acidmanic.localpactbroker.models.Dto;
import com.acidmanic.localpactbroker.models.Link;
import com.acidmanic.localpactbroker.models.VerificationResult;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

/**
 *
 * @author diego
 */
@Controller
@Path("verification")
public class VerificationResultController {

    private final Logger logger;

    public VerificationResultController(Logger logger) {
        this.logger = logger;
    }

    @GET
    @POST
    @PUT
    @DELETE
    @OPTIONS
    @PATCH
    @Path("{rawuri:.*}")
    @Produces(MediaType.APPLICATION_JSON)
    public VerificationResult logEverything(@PathParam("rawuri") String rawUri) {

        HttpServletRequest request = ResteasyProviderFactory.getContextData(HttpServletRequest.class);

        logger.info("Path: " + rawUri);
        logger.info("Method: " + request.getMethod());

        String data = "";

        try {

            InputStream stream = request.getInputStream();

            int value = stream.read();

            while (value != -1) {

                data += (char) value;

                value = stream.read();
            }

        } catch (Exception e) {
        }

        logger.info("Data:");

        logger.info(data);

        VerificationResult result = new VerificationResult();

        result.get_links().put("self",
                new Link("Pact",
                        "Pact between me (v1.0.0) and they",
                        "http://localhost:8585/verification/fetch"));

        return result;
    }
    
    
}
