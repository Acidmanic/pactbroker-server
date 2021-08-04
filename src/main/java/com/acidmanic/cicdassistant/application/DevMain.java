/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.application;

import com.acidmanic.lightweight.logger.ConsoleLogger;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author diego
 */
public class DevMain {

    public static void main(String[] args) {

        ConsoleLogger logger = new ConsoleLogger();

        logger.warning("-----------------------------------------------");
        logger.warning("-----------------------------------------------");
        logger.warning("The Application is running under development witing.");
        logger.warning("-----------------------------------------------");
        logger.warning("-----------------------------------------------");
        logger.info("Current Directory: " + new File(".").toPath().toAbsolutePath().normalize().toString());
        logger.log("Preparing develop workspace:");

        copyContentInto(new File("../dev-workspace"), new File("../target"));
        
        logger.info("workspace ready. starting application");
        
        Main.main(args);

    }

    private static void copyContentInto(File sourceDirectory, File destinationDirectory) {

        File[] content = sourceDirectory.listFiles();

        for (File file : content) {

            copyInto(file.toPath(), destinationDirectory.toPath());
        }
    }

    private static void copyInto(Path source, Path destinationDirectory) {

        if (!destinationDirectory.toFile().exists()) {

            destinationDirectory.toFile().mkdirs();
        }

        String name = source.getFileName().toString();

        if (source.toFile().isFile()) {

            Path destinationFile = destinationDirectory.resolve(name);

            try {
                Files.copy(source, destinationFile, StandardCopyOption.REPLACE_EXISTING);

            } catch (IOException ex) {

            }
        }

        if (source.toFile().isDirectory()) {

            File[] files = source.toFile().listFiles();

            Path deeperDestination = destinationDirectory.resolve(name);

            for (File file : files) {

                copyInto(file.toPath(), deeperDestination);
            }
        }
    }
}
