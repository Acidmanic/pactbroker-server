/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.controllers;

import com.acidmanic.cicdassistant.application.services.web.Controller;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author diego
 */
@Controller
@Path("/")
public class RootController {

    
    @Path("favicon.ico")
    @Produces("image/x-icon")
    public void getFavIcon() {

        
    }
}
