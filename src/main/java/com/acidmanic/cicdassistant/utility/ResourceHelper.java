/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.utility;

import com.acidmanic.delegates.Function1;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author diego
 */
public class ResourceHelper {

    private Class type;
    private final Path executableFilePath;
    private final Path executionDirectory;
    private boolean valid;

    @SuppressWarnings("UseSpecificCatch")
    public ResourceHelper() {

        this.type = getClass();

        URI jarUri = URI.create(".");

        try {
            jarUri = type.getProtectionDomain().getCodeSource().getLocation().toURI();

            this.valid = true;

        } catch (Exception e) {

            this.valid = false;
        }

        this.executableFilePath = Paths.get(jarUri).toAbsolutePath().normalize();

        this.executionDirectory = this.executableFilePath.getParent();

    }

    public Path getExecutableFilePath() {
        return executableFilePath;
    }

    public Path getExecutionDirectory() {
        return executionDirectory;
    }

    public boolean isValid() {
        return valid;
    }

    @SuppressWarnings({"UseSpecificCatch", "null"})
    public boolean exportLargeResource(String resourceName, File outputFile) {

        InputStream stream = null;
        OutputStream resStreamOut = null;

        boolean success = true;

        try {
            if (this.executableFilePath.toFile().isDirectory()) {

                stream = new FileInputStream(this.executableFilePath.resolve(resourceName).toFile());
            } else {

                if (!resourceName.startsWith("/")) {
                    resourceName = "/" + resourceName;
                }
                stream = this.type.getResourceAsStream(resourceName);
            }
            if (stream == null) {
                //Resource is not found
                return false;
            }

            int readBytes;
            byte[] buffer = new byte[4096];
            resStreamOut = new FileOutputStream(outputFile);

            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }

        } catch (Exception ex) {
            success = false;
        } finally {
            try {
                stream.close();
            } catch (Exception e) {
            }
            try {
                resStreamOut.close();
            } catch (Exception e) {
            }
        }

        return success;
    }

    public byte[] readResource(String resourceName) {
        try {
            InputStream inputStream = null;

            if (this.executableFilePath.toFile().isDirectory()) {

                inputStream = new FileInputStream(this.executableFilePath.resolve(resourceName).toFile());
            } else {

                if (!resourceName.startsWith("/")) {
                    resourceName = "/" + resourceName;
                }
                inputStream = this.type.getResourceAsStream(resourceName);
            }
            int readBytes;
            byte[] buffer = new byte[4096];
            ByteArrayOutputStream byteContents = new ByteArrayOutputStream();

            while ((readBytes = inputStream.read(buffer)) > 0) {
                byteContents.write(buffer, 0, readBytes);
            }

            inputStream.close();

            return byteContents.toByteArray();

        } catch (Exception e) {
        }
        return null;
    }

    private boolean writeFile(File file, byte[] content) {
        try {

            if (file.exists()) {
                file.delete();
            }

            Files.write(file.toPath(), content, StandardOpenOption.CREATE);

            return true;
        } catch (Exception e) {
        }
        return false;
    }

    @SuppressWarnings({"UseSpecificCatch", "null"})
    public boolean exportResourceTextFile(
            String resourceName,
            File outputFile) {

        return exportResourceTextFile(resourceName, outputFile, s -> s);
    }

    @SuppressWarnings({"UseSpecificCatch", "null"})
    public boolean exportResourceTextFile(
            String resourceName,
            File outputFile,
            Function1<String, String> manipulateAsString) {

        byte[] byteContent = readResource(resourceName);

        if (byteContent != null) {

            String stringContent = new String(byteContent);

            stringContent = manipulateAsString.perform(stringContent);

            byteContent = stringContent.getBytes();

            if (writeFile(outputFile, byteContent)) {
                return true;
            }
        }
        return false;
    }

}
