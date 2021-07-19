/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.controllers;

import com.acidmanic.cicdassistant.application.configurations.Configurations;
import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.cicdassistant.application.services.web.Controller;
import com.acidmanic.cicdassistant.models.Dto;
import com.acidmanic.cicdassistant.models.SendMailRequest;
import com.acidmanic.cicdassistant.services.MailMimeTypes;
import com.acidmanic.cicdassistant.services.SmtpClient;
import com.acidmanic.cicdassistant.storage.TokenStorage;
import com.acidmanic.delegates.Function;
import java.util.Base64;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author diego
 */
@Controller
@Path("mail")
public class MailController extends ControllerBase {

    private final SmtpClient smtpClient;
    private final Configurations configurations;

    public MailController(SmtpClient smtpClient, Configurations configurations, TokenStorage tokenStorage, Logger logger) {
        super(tokenStorage, logger);
        this.smtpClient = smtpClient;
        this.configurations = configurations;
    }

    @POST
    @Path("/send")
    @Produces("application/json")
    public Dto send(@HeaderParam("token") String token, SendMailRequest request) {

        Function<Dto<Object>> innerCode = () -> {

            String username = request.getFrom();

            if (configurations.getCredencials().containsKey(username)) {

                String password = configurations.getCredencials().get(username);

                smtpClient.authenticate(username, password);
            }

            Dto<Object> result = new Dto();

            result.setFailure(true);

            MailMimeTypes mime = request.isHtml() ? MailMimeTypes.Html : MailMimeTypes.PlainText;

            String body = "";

            try {

                body = new String(Base64.getDecoder().decode(request.getBase64Content()));

            } catch (Exception e) {

                result.setError(e);

                return result;
            }

            String recipients = appendAll(request.getRecipients());

            if (this.smtpClient.send(request.getFrom(),
                    recipients,
                    body, request.getSubject(), mime)) {

                result.setFailure(false);
            }

            return result;
        };

        Dto response = authorize(token, innerCode);

        return response;
    }

    private String appendAll(String[] addresses) {

        String recipients = "";
        String sep = "";

        for (String recipient : addresses) {
            
            recipients += sep + recipient;

            sep = " , ";
        }
        return recipients;
    }

}
