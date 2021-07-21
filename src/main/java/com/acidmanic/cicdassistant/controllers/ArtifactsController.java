/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.controllers;

import com.acidmanic.cicdassistant.controllers.models.FileUploadForm;
import com.acidmanic.cicdassistant.application.services.web.Controller;
import com.acidmanic.cicdassistant.models.Dto;
import com.acidmanic.cicdassistant.services.ArtifactManager;
import com.acidmanic.cicdassistant.services.MultipartFileSender;
import com.acidmanic.cicdassistant.storage.TokenStorage;
import com.acidmanic.delegates.Function;
import com.acidmanic.lightweight.logger.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import static javax.ws.rs.client.Entity.form;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

/**
 *
 * @author diego
 */
@Path("artifacts")
@Controller
public class ArtifactsController extends ControllerBase {

    private final ArtifactManager artifactManager;

    public ArtifactsController(ArtifactManager artifactManager, TokenStorage tokenStorage, Logger logger) {
        super(tokenStorage, logger);
        this.artifactManager = artifactManager;
    }

    @GET
    @Path("{rawuri:.*}")
    public Response get(@PathParam("rawuri") String rawUri) {

        HttpServletRequest request = ResteasyProviderFactory.getContextData(HttpServletRequest.class);
        HttpServletResponse response = ResteasyProviderFactory.getContextData(HttpServletResponse.class);

        try {
            MultipartFileSender.fromPath(this.artifactManager.mapPathVerify(rawUri).getValue())
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

    @POST
    @Path("/upload")
    @Consumes("multipart/form-data")
    public Response uploadFile(
            @MultipartForm FileUploadForm form,
            @HeaderParam("token") String token) {

        Function<Dto<Response>> code = () -> {

            String fileName = form.getFileName() == null ? "Unknown" : form.getFileName();

            this.artifactManager.writeArtifact(fileName, form.getFileData());

            Response response = Response.status(200)
                    .entity("uploadFile is called, Uploaded file name: "
                            + fileName + "\n")
                    .build();

            return new Dto<>(response);
        };

        Dto<Response> authorizedResult = authorize(token, code);

        if (authorizedResult.isFailure()) {

            return Response.status(Response.Status.UNAUTHORIZED).build();
        } else {

            return authorizedResult.getModel();
        }
    }

    @DELETE
    @Path("/")
    @Produces("application/json")
    public Response deleteArtifact(
            @QueryParam("filename") String filename,
            @HeaderParam("token") String token) {

        Function<Dto<Object>> code = () -> {

            this.artifactManager.deleteArtifact(filename);

            return new Dto<>();
        };

        authorize(token, code);

        return Response.status(Response.Status.OK).build();

    }
    
    @DELETE
    @Path("/clear")
    @Produces("application/json")
    public Response clearArtifacts(
            @HeaderParam("token") String token) {

        Function<Dto<Object>> code = () -> {

            this.artifactManager.clearArtifacts();

            return new Dto<>();
        };

        authorize(token, code);

        return Response.status(Response.Status.OK).build();

    }

}
