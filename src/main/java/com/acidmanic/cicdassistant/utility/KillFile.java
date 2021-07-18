/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.utility;

import java.io.File;

/**
 *
 * @author diego
 */
public class KillFile {

    private final File file;

    public KillFile() {

        this.file = new ResourceHelper().getExecutionDirectory()
                .resolve(".kill").toFile();
    }

    public boolean isPresent() {
        return this.file.exists();
    }

    public void delete() {
        try {
            if (this.file.exists()) {
                this.file.delete();
            }
        } catch (Exception e) {
        }
    }

}
