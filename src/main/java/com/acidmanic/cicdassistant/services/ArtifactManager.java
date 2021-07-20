/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.services;

import com.acidmanic.cicdassistant.utility.ResourceHelper;
import com.acidmanic.cicdassistant.utility.Result;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author diego
 */
public class ArtifactManager {

    private final Path artifactsDirectory;

    public ArtifactManager() {

        this.artifactsDirectory = new ResourceHelper()
                .getExecutableFilePath()
                .resolve("artifacts");

        File artifactsDir = this.artifactsDirectory.toFile();

        if (!artifactsDir.exists()) {
            artifactsDir.mkdirs();
        }
    }

    public Result<Path> mapPath(String uri) {

        Path artifactPath = this.artifactsDirectory.resolve(uri);

        if (artifactPath.toFile().exists()) {

            return new Result<>(true, artifactPath);
        }

        return new Result(false, null);
    }

    public Result<byte[]> readArtifact(String uri) {

        Path artifactPath = this.artifactsDirectory.resolve(uri);

        if (artifactPath.toFile().exists()) {

            try {

                byte[] data = Files.readAllBytes(artifactPath);

                return new Result<>(true, data);

            } catch (Exception e) {
            }
        }

        return new Result(false, null);
    }

    public boolean writeArtifact(String uri, byte[] data) {

        Path artifactPath = this.artifactsDirectory.resolve(uri);
        try {

            if (artifactPath.toFile().exists()) {
                artifactPath.toFile().delete();
            }

            Files.write(artifactPath, data, StandardOpenOption.CREATE);

            return true;
        } catch (Exception e) {
        }
        return false;
    }

}
