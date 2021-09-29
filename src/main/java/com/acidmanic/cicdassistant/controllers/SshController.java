/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.controllers;

import com.acidmanic.cicdassistant.application.configurations.Configurations;
import com.acidmanic.cicdassistant.application.configurations.SshSession;
import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.cicdassistant.application.services.web.Controller;
import com.acidmanic.cicdassistant.infrastructure.contracts.SshCommandExecuter;
import com.acidmanic.cicdassistant.models.Dto;
import com.acidmanic.cicdassistant.storage.TokenStorage;
import com.acidmanic.cicdassistant.utility.Result;
import com.acidmanic.cicdassistant.utility.StringUtils;
import com.acidmanic.delegates.Function;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author diego
 */
@Controller
@Path("ssh")
public class SshController extends ControllerBase {

    private final Configurations configurations;
    private final SshCommandExecuter sshCommandExecuter;

    public SshController(Configurations configurations, SshCommandExecuter sshCommandExecuter, TokenStorage tokenStorage, Logger logger) {
        super(tokenStorage, logger);
        this.configurations = configurations;
        this.sshCommandExecuter = sshCommandExecuter;
    }

    @POST
    @Path("/")
    @Produces("application/json")
    public Dto<List<String>> executeSshCommands(
            @HeaderParam("token") String token,
            @FormParam("host") String host,
            @FormParam("command") String command) {

        Function<Dto<List<String>>> innerCode = () -> {

            Dto<List<String>> result = new Dto<>(null);

            SshSession sessionParams = findSessionParameters(host, configurations);

            if (sessionParams == null) {
                
                result.setFailure(true);
                
                result.setError("No Ssh Session is Configured for host: " + host);
            }
            else
            {

                Result<List<String>> sshReponse = this.sshCommandExecuter
                        .executeCommand(command, sessionParams);

                result.setFailure(!sshReponse.isSuccessfull());

                result.setModel(sshReponse.getValue());
            }
            return result;

        };

        Dto response = authorize(token, innerCode);

        return response;
    }

    private SshSession findSessionParameters(String host, Configurations configurations) {

        if (!StringUtils.isNullOrEmpty(host)) {

            if (!this.configurations.getSshSessions().isEmpty()) {

                int port = 22;

                int st = host.indexOf(":");

                if (st > -1) {

                    String portString = host.substring(st + 1, host.length());

                    try {

                        port = Integer.parseInt(portString);

                    } catch (Exception e) {

                        return null;
                    }

                    host = host.substring(0, st);
                }
                if (!StringUtils.isNullOrEmpty(host)) {

                    for (SshSession session : configurations.getSshSessions()) {

                        if (host.equalsIgnoreCase(session.getHost())
                                && port == session.getPort()) {

                            return session;
                        }
                    }
                }
            }

        }
        return null;
    }

}
