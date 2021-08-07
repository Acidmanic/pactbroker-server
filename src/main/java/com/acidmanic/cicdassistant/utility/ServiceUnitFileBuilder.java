/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.utility;

import java.io.File;
import java.nio.file.Path;

/**
 *
 * @author diego
 */
public class ServiceUnitFileBuilder {

    private String startCommand;
    private String username;
    private String applicationDescription;
    private Path workingDirectory;

    public ServiceUnitFileBuilder() {

        this.workingDirectory = new ResourceHelper().getExecutionDirectory()
                .toAbsolutePath().normalize();

        this.username = System.getProperty("user.name");

        if (StringUtils.isNullOrEmpty(username)) {
            this.username = "root";
        }

        this.applicationDescription = createDescription();

    }

    private String createDescription() {

        String fullName = this.getClass().getName();

        int simpleLength = this.getClass().getSimpleName().length();

        String packageName = fullName.substring(0, fullName.length() - simpleLength);

        packageName = StringUtils.stripSides(packageName, ".");

        return packageName;
    }

    public ServiceUnitFileBuilder runsCommand(String executeCommand) {

        this.startCommand = executeCommand;

        return this;
    }

    public ServiceUnitFileBuilder runsJar(String jarFileName) {

        Path jarFile = this.workingDirectory.resolve(jarFileName);

        return this.runsJar(jarFile);
    }

    public ServiceUnitFileBuilder runsJar() {

        Path jarFile = new ResourceHelper().getExecutableFilePath()
                .toAbsolutePath().normalize();

        return this.runsJar(jarFile);
    }

    public ServiceUnitFileBuilder runsJar(Path jarFile) {

        String javaPath = getJavaPath();

        this.startCommand = javaPath
                + " -jar "
                + jarFile.toString();

        return this;
    }

    public ServiceUnitFileBuilder byUser(String username) {

        this.username = username;

        return this;
    }

    public ServiceUnitFileBuilder byServiceDescription(String description) {

        this.applicationDescription = description;

        return this;
    }

    public ServiceUnitFileBuilder seatAt(Path workingDirectory) {

        this.workingDirectory = workingDirectory;

        return this;
    }

    public ServiceUnitFile build() {

        return new ServiceUnitFile(startCommand,
                username, applicationDescription,
                workingDirectory);
    }

    private String getJavaPath() {

        Bash b = new Bash();

        String java = b.syncRun("which java");

        if (!StringUtils.isNullOrEmpty(java)) {

            java = java.trim();

            try {
                File javaFile = new File(java);

                if (javaFile.exists()) {

                    return javaFile.toPath().toAbsolutePath().normalize().toString();
                }

            } catch (Exception e) {
            }
        }
        return null;
    }

}
