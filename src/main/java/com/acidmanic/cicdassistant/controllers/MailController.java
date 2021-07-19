/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.controllers;

import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.cicdassistant.application.services.web.Controller;
import com.acidmanic.cicdassistant.services.SmtpClient;
import com.acidmanic.cicdassistant.storage.TokenStorage;
import javax.ws.rs.Path;

/**
 *
 * @author diego
 */
@Controller
@Path("mail")
public class MailController extends ControllerBase {
    
    
    private final SmtpClient smtpClient;

    public MailController(SmtpClient smtpClient, TokenStorage tokenStorage, Logger logger) {
        super(tokenStorage, logger);
        this.smtpClient = smtpClient;
    }

    

    

}
