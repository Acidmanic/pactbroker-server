/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.commands;

import com.acidmanic.commandline.commands.CommandBase;

/**
 *
 * @author diego
 */
public class Token extends CommandBase {

    @Override
    protected String getUsageDescription() {
        return "This command generates and overrides access token";
    }

    @Override
    protected String getArgumentsDesciption() {
        return "";
    }

    @Override
    public void execute(String[] args) {
        ApplicationContext context = getContext();

        String token = context.getTokenGenerator().generateToken();

        boolean success = context.getTokenStorage().write(token);

        if (success) {

            getLogger().log("=============================================");
            getLogger().log("use following token for authentication over network:");
            getLogger().log("---------------------------------------------");
            getLogger().info(token);
            getLogger().log("=============================================");
        } else {
            getLogger().error("Unable to write a new token to token storage.");
        }

    }

    @Override
    public boolean hasArguments() {
        return false;
    }

}
