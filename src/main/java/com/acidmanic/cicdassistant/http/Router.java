/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.http;

import java.io.File;
import java.nio.file.Path;

/**
 *
 * @author diego
 */
public interface Router {

    File mapPath(String uri);

    File getRoot();

    void setRoot(File root);
    
    Path unMapPath(File file);
}
