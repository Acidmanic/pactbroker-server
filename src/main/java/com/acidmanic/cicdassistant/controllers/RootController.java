/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.controllers;

import com.acidmanic.cicdassistant.application.services.web.Controller;
import com.acidmanic.cicdassistant.services.FavIconService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author diego
 */
@Controller
@Path("/")
public class RootController {

    private final FavIconService favIconService;

    public RootController(FavIconService favIconService) {
        this.favIconService = favIconService;
    }

    @Path("favicon.ico")
    @GET
    public Response getFavIcon() {

        byte[] data = this.favIconService.getFavIconBytes();

        Response res = Response
                .ok(data, "image/x-icon")
                .build();

        return res;
    }
}
