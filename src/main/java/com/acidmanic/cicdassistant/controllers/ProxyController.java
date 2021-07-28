/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.controllers;

import com.acidmanic.cicdassistant.application.services.web.Controller;
import com.acidmanic.lightweight.logger.ConsoleLogger;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author diego
 */
@Path("proxy")
@Controller
public class ProxyController {

    @GET
    @Path("/")
    public Response getTextFileByUrl(@QueryParam("url") String urlString) {

        try {
            URL url = new URL(urlString);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            InputStream input = con.getInputStream();

            String mime = con.getHeaderField("Content-Type");

            //StringBuilder content = new StringBuilder();
            
            ByteArrayOutputStream content = new ByteArrayOutputStream();

            int value = input.read();

            while (value != -1) {

                //content.append((char) value);
                
                content.write(value);

                value = input.read();
            }
            input.close();
            con.disconnect();

            return Response
                    .status(200)
                    .entity(content.toByteArray())
                    .header("Content-Type", mime)
                    .build();

        } catch (Exception ex) {
            new ConsoleLogger().log(urlString);
        }
        return Response
                .status(404)
                .build();
    }
}
