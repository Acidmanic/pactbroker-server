/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.controllers;

import com.acidmanic.cicdassistant.controllers.models.FileUploadForm;
import com.acidmanic.cicdassistant.application.services.web.Controller;
import com.acidmanic.cicdassistant.services.ArtifactManager;
import com.acidmanic.cicdassistant.services.MultipartFileSender;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
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
    public Response uploadFile(@MultipartForm FileUploadForm form) {

        String fileName = form.getFileName() == null ? "Unknown" : form.getFileName();

        this.artifactManager.writeArtifact(fileName, form.getFileData());

        return Response.status(200)
                .entity("uploadFile is called, Uploaded file name: "
                        + fileName + "\n")
                .build();
    }

}
