/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.controllers;

import com.acidmanic.cicdassistant.application.services.web.Controller;
import com.acidmanic.cicdassistant.services.routing.AssetsRouter;
import com.acidmanic.cicdassistant.services.routing.DirectoryServeingRouter;
import com.acidmanic.cicdassistant.utility.web.MimeTypeTable;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author diego
 */
@Controller
@Path("assets")
public class WebAssetsController {

    private final AssetsRouter assetsRouter;

    public WebAssetsController(AssetsRouter assetsRouter) {
        this.assetsRouter = assetsRouter;
    }

    @GET
    @Path("{rawuri:.*}")
    public Response get(
            @PathParam("rawuri") String rawUri) {

        
        String mime = new MimeTypeTable().getMimeTypeForUri(rawUri);
        
        byte[] content = readFile(rawUri);
        
        return Response
                .status(200)
                .type(mime)
                .entity(content)
                .build();

    }

    private byte[] readFile(String uri) {

        File file = this.assetsRouter.mapPath(uri);

        return tryReadAsBytes(file.toPath());
    }

    private byte[] tryReadAsBytes(java.nio.file.Path file) {
        try {

            return Files.readAllBytes(file);

        } catch (Exception e) {
        }
        return notFound();
    }

    private byte[] notFound() {

        String notFoundString = "<html><head><title>"
                + "Not Found</title></head><body>"
                + "<h1>The page you are looking for, does not exists</h1>"
                + "</body></html>";
        return notFoundString.getBytes(Charset.forName("utf-8"));
    }

}
