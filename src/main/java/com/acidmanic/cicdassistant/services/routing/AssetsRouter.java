/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.services.routing;

import com.acidmanic.cicdassistant.http.Router;
import com.acidmanic.cicdassistant.utility.ResourceHelper;
import java.io.File;
import java.nio.file.Path;

/**
 *
 * @author diego
 */
public class AssetsRouter implements Router {

    private enum Existance {
        None,
        FileOnly,
        DirectoryOnly,
        FileAndDirectory
    }

    private Path root;

    public AssetsRouter(Path root) {
        this.root = root.toAbsolutePath().normalize();
        initializeRoot();
    }

    public AssetsRouter() {
        this("assets");
    }

    public AssetsRouter(String wwwroot) {
        this(new ResourceHelper()
                .getExecutionDirectory()
                .resolve(wwwroot)
                .toAbsolutePath()
                .normalize());
    }

    @Override
    public File mapPath(String uri) {

        Path resourcePath = this.root.resolve(uri).toAbsolutePath().normalize();

        return resourcePath.toFile();
    }

    @Override
    public File getRoot() {

        return this.root.toFile();
    }

    @Override
    public void setRoot(File root) {

        this.root = root.toPath();

        initializeRoot();
    }

    private void initializeRoot() {

        File rootDir = getRoot();

        if (!rootDir.exists()) {
            rootDir.mkdirs();
        }
    }

    @Override
    public Path unMapPath(File file) {

        return this.root.relativize(file.toPath());
    }
}
