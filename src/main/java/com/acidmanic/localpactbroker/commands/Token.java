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
public class Token extends CommandBase{

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean hasArguments() {
        return false;
    }
    
}
