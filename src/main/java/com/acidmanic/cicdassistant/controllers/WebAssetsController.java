/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.controllers;

import com.acidmanic.cicdassistant.application.services.web.Controller;
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
    
    
    
    @GET
    @Path("{rawuri:.*}")
    public Response get(
            @PathParam("rawuri") String rawUri) {
        
        
        return null;

    }
}
