/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.controllers;

import com.acidmanic.cicdassistant.application.services.web.Controller;
import com.acidmanic.cicdassistant.services.ArtifactManager;
import com.acidmanic.cicdassistant.services.MultipartFileSender;
import com.acidmanic.cicdassistant.utility.Result;
import com.acidmanic.cicdassistant.utility.web.MimeTypeTable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

/**
 *
 * @author diego
 */
@Path("artifacts")
@Controller
public class ArtifactsController {

    private final ArtifactManager artifactManager;

    public ArtifactsController(ArtifactManager artifactManager) {
        this.artifactManager = artifactManager;
    }

    @GET
    @Path("{rawuri:.*}")
    public Response get(@PathParam("rawuri") String rawUri) {

        HttpServletRequest request = ResteasyProviderFactory.getContextData(HttpServletRequest.class);
        HttpServletResponse response = ResteasyProviderFactory.getContextData(HttpServletResponse.class);

        try {
            MultipartFileSender.fromPath(this.artifactManager.mapPath(rawUri).getValue())
                    .with(request)
                    .with(response)
                    .serveResource();

            Response res = Response
                    .status(response.getStatus())
                    .build();

            return res;

        } catch (Exception e) {
        }

        return Response
                .status(404)
                .build();
    }

}
