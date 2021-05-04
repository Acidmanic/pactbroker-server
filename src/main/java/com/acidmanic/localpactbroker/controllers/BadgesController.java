/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.controllers;

import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.localpactbroker.application.services.web.Controller;
import com.acidmanic.localpactbroker.models.BadgeMap;
import com.acidmanic.localpactbroker.models.BadgeType;
import com.acidmanic.localpactbroker.models.Dto;
import com.acidmanic.localpactbroker.storage.BadgeStorage;
import com.acidmanic.localpactbroker.storage.TokenStorage;
import com.acidmanic.localpactbroker.utility.Result;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author diego
 */
@Controller
@Path("/badges")
public class BadgesController extends ControllerBase {

    private final BadgeStorage badgeStorage;

    public BadgesController(BadgeStorage badgeStorage, TokenStorage tokenStorage, Logger logger) {
        super(tokenStorage, logger);
        this.badgeStorage = badgeStorage;
    }

    @GET
    @Path("{tag}")
    public Response getBadge(@PathParam("tag") String tag) {

        Result<BadgeMap> badges = this.badgeStorage.read();

        BadgeType type = BadgeType.Unknown;

        if (badges.isSuccessfull()) {
            if (badges.getValue().containsKey(tag)) {
                type = badges.getValue().get(tag);
            }
        }

        String data = new Badges().getBadgeSvg(type);

        Response res = Response
                .ok(data, "image/svg+xml")
                .build();

        return res;
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Dto<BadgeMap> setBadges(BadgeMap badges, @HeaderParam("token") String token) {

        return super.authorize(token, () -> {

            Dto< BadgeMap> response = new Dto<>();

            boolean success = this.badgeStorage.write(badges);

            response.setFailure(!success);

            response.setModel(badges);

            return response;
        });
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Dto<BadgeMap> getBadges() {

        Dto<BadgeMap> response = new Dto<>();

        Result<BadgeMap> result = this.badgeStorage.read();

        response.setFailure(!result.isSuccessfull());

        response.setModel(result.getValue());

        return response;
    }

}