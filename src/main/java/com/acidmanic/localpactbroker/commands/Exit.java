/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.commands;

import com.acidmanic.commandline.commands.CommandBase;

/**
 *
 * @author diego
 */
public class Exit extends CommandBase {

    @Override
    protected String getUsageDescription() {
        return "Exits the application.";
    }

    @Override
    protected String getArgumentsDesciption() {
        return "";
    }

    @Override
    public void execute(String[] args) {
        ApplicationContext context = getContext();

        context.getApplicationswitch().shutdown();
    }

    @Override
    public boolean hasArguments() {
        return false;
    }

}
