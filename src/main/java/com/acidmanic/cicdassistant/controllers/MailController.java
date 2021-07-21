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
import com.acidmanic.cicdassistant.models.RegisterTemplateDto;
import com.acidmanic.cicdassistant.models.SelfContainedMailRequest;
import com.acidmanic.cicdassistant.models.TemplateBaseMailRequest;
import com.acidmanic.cicdassistant.services.HtmlTemplateManager;
import com.acidmanic.cicdassistant.services.MailMimeTypes;
import com.acidmanic.cicdassistant.services.SmtpClient;
import com.acidmanic.cicdassistant.storage.TokenStorage;
import com.acidmanic.cicdassistant.utility.Result;
import com.acidmanic.delegates.Function;
import java.nio.charset.Charset;
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
    private final HtmlTemplateManager htmlTemplateManager;

    public MailController(SmtpClient smtpClient, Configurations configurations, HtmlTemplateManager htmlTemplateManager, TokenStorage tokenStorage, Logger logger) {
        super(tokenStorage, logger);
        this.smtpClient = smtpClient;
        this.configurations = configurations;
        this.htmlTemplateManager = htmlTemplateManager;
    }

    @POST
    @Path("/send-selfcontained")
    @Produces("application/json")
    public Dto sendSelfContained(@HeaderParam("token") String token, SelfContainedMailRequest request) {

        Function<Dto<Object>> innerCode = () -> {

            String username = request.getFrom();

            if (configurations.getCredentials().containsKey(username)) {

                String password = configurations.getCredentials().get(username);

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

    @POST
    @Path("/send-bytemplate")
    @Produces("application/json")
    public Dto sendByTemplate(@HeaderParam("token") String token, TemplateBaseMailRequest request) {

        Function<Dto<Object>> innerCode = () -> {

            String username = request.getFrom();

            if (configurations.getCredentials().containsKey(username)) {

                String password = configurations.getCredentials().get(username);

                smtpClient.authenticate(username, password);
            }

            Dto<Object> result = new Dto();

            result.setFailure(true);

            Result<String> bodyResult = this.htmlTemplateManager
                    .readHtml(request.getTemplateName(),
                            request.getSubstitutions());

            if (!bodyResult.isSuccessfull()) {

                result.setError("Error reading template");

                return result;
            }

            String recipients = appendAll(request.getRecipients());

            if (this.smtpClient.send(request.getFrom(),
                    recipients,
                    bodyResult.getValue(),
                    request.getSubject(), MailMimeTypes.Html)) {

                result.setFailure(false);
            }

            return result;
        };

        Dto response = authorize(token, innerCode);

        return response;
    }

    @POST
    @Path("/register-template")
    @Produces("application/json")
    public Dto sendByTemplate(@HeaderParam("token") String token, RegisterTemplateDto request) {

        Function<Dto<Object>> innerCode = () -> {

            Dto<Object> result = new Dto();

            result.setFailure(true);

            try {

                String content = new String(
                        Base64.getDecoder().decode(request.getBase64Content()),
                        Charset.forName("utf-8")
                );

                if (this.htmlTemplateManager.saveTemplate(content, request.getTemplateName())) {
                        
                    result.setFailure(false);
                    result.setModel("Template Registered");
                    result.setError(null);
                    return result;
                }

            } catch (Exception e) {

                result.setError(e);
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
