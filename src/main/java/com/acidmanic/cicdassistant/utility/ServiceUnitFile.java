/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.utility;

import java.nio.file.Path;

/**
 *
 * @author diego
 */
public class ServiceUnitFile {

    private String startCommand;
    private String username;
    private String applicationDescription;
    private Path workingDirectory;

    public ServiceUnitFile() {
    }

    public ServiceUnitFile(String startCommand, String username, String applicationDescription, Path workingDirectory) {
        this.startCommand = startCommand;
        this.username = username;
        this.applicationDescription = applicationDescription;
        this.workingDirectory = workingDirectory;
    }

    @Override
    public String toString() {

        StringBuilder content = new StringBuilder();

        content.append("[Unit]").append("\n");
        content.append("Description=").append(this.applicationDescription).append("\n");
        content.append("[Service]").append("\n");
        content.append("User=").append(this.username).append("\n");

        Path workspace = this.workingDirectory.toAbsolutePath().normalize();

        String execStart = workspace.resolve(this.startCommand).toString();

        content.append("WorkingDirectory=").append(workspace.toString()).append("\n");

        content.append("ExecStart=").append(execStart).append("\n");

        content.append("SuccessExitStatus=143\n"
                + "TimeoutStopSec=10\n"
                + "Restart=on-failure\n"
                + "RestartSec=5\n"
                + "\n"
                + "[Install]\n"
                + "WantedBy=multi-user.target");
        return content.toString();
    }

}
