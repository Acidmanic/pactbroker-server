/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.services.routing;

import com.acidmanic.cicdassistant.utility.ResourceHelper;
import java.io.File;
import java.nio.file.Path;

/**
 *
 * @author diego
 */
public class WikiRouter implements Router {

    private enum Existance {
        None,
        FileOnly,
        DirectoryOnly,
        FileAndDirectory
    }

    private Path root;

    private final String[] defaultDocuments = new String[]{"home.md", "index.md", "index.html", "index.htm"};

    public WikiRouter(Path root) {
        this.root = root.toAbsolutePath().normalize();
        initializeRoot();
    }

    public WikiRouter() {
        this("wikiroot");
    }

    public WikiRouter(String wwwroot) {
        this(new ResourceHelper()
                .getExecutionDirectory()
                .resolve(wwwroot)
                .toAbsolutePath()
                .normalize());
    }

    @Override
    public File mapPath(String uri) {

        Path resourcePath = this.root.resolve(uri).toAbsolutePath().normalize();

        Existance uriExistance = exists(resourcePath);

        if (uriExistance == Existance.None || uriExistance == Existance.DirectoryOnly) {

            File mdExtensioned = findMdExtensionedOf(resourcePath);

            if (mdExtensioned.exists()) {

                return mdExtensioned;
            }
        }
        Existance extensionCheckedUriExistance = exists(resourcePath);

        if (extensionCheckedUriExistance == Existance.DirectoryOnly) {

            String defDoc = findDefaultDocumentAt(resourcePath);

            if (defDoc == null) {
                //Which does not exist. But is being returned instead of null
                return resourcePath.toFile();
            }
            resourcePath = resourcePath.resolve(defDoc);
        }
        File resourceFile = resourcePath.toFile();

        if (resourceFile.exists()) {

            return resourceFile;
        }
        //Which does not exist. But is being returned instead of null
        return resourceFile;
    }

    private String findDefaultDocumentAt(Path uri) {

        File[] children = uri.toFile().listFiles();

        for (String defDoc : defaultDocuments) {

            if (children != null) {

                for (File child : children) {

                    if (defDoc.equalsIgnoreCase(child.getName())) {

                        return child.getName();
                    }
                }
            }
        }
        return null;
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

    private File findMdExtensionedOf(Path resourcePath) {

        File directory = resourcePath.getParent().toFile();

        String name = resourcePath.getFileName().toString();

        String[] expectedNames = {name + ".md", name + ".Md", name + ".MD", name + ".mD"};

        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {

                for (String expected : expectedNames) {

                    if (expected.equals(file.getName())) {

                        return file;
                    }
                }
            }
        }
        // Which does not exists, but returning this instead of null
        return directory.toPath().resolve(name + ".md").toFile();
    }

    @Override
    public Path unMapPath(File file) {

        return this.root.relativize(file.toPath());
    }

    private Existance exists(Path path) {

        return exists(path.toFile());
    }

    private Existance exists(File file) {

        if (file.exists()) {

            if (file.isDirectory()) {

                if (file.isFile()) {

                    return Existance.FileAndDirectory;
                } else {

                    return Existance.DirectoryOnly;
                }
            } else {
                return Existance.FileOnly;
            }
        }
        return Existance.None;
    }

}
