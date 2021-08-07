/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.commands;

import com.acidmanic.cicdassistant.utility.Bash;
import com.acidmanic.cicdassistant.utility.ServiceUnitFileBuilder;
import com.acidmanic.commandline.commands.CommandBase;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author diego
 */
public class InstallService extends CommandBase {

    @Override
    protected String getUsageDescription() {

        return "This command will install a systemd service for "
                + "cicd-assistant application where it is currently seat.";
    }

    @Override
    protected String getArgumentsDesciption() {
        return "no arguments";
    }

    @Override
    public void execute(String[] args) {

        String serviceUnitfileContent = new ServiceUnitFileBuilder()
                .byServiceDescription("Cicd Assistant Service")
                .runsJar()
                .build()
                .toString();

        try {

            damnWrite(serviceUnitfileContent, "/etc/systemd/system/cicd-assistant.service");

            getLogger().warning(new Bash().syncRun("systemctl daemon-reload"));
            getLogger().warning(new Bash().syncRun("systemctl enable cicd-assistant.service"));
            getLogger().warning(new Bash().syncRun("systemctl start cicd-assistant"));
            getLogger().warning(new Bash().syncRun("systemctl status cicd-assistant"));
            
            getLogger().info("Installed successfully");

        } catch (Exception e) {

            getLogger().error("Error installing daemon: " + e.getClass().getSimpleName());

            getLogger().error(e.getMessage());
        }

    }

    @Override
    public boolean hasArguments() {
        return false;
    }

    private void damnWrite(String content, String path) throws Exception {

        File file = new File(path);

        if (file.exists()) {
            file.delete();
        }

        Files.write(file.toPath(), content.getBytes(), StandardOpenOption.CREATE);
    }

}
