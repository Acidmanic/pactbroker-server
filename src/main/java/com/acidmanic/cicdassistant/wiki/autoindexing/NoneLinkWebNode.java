/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.autoindexing;

import java.io.File;

/**
 *
 * @author diego
 */
public class NoneLinkWebNode extends WebNode {

    private final String title;

    public NoneLinkWebNode(String title) {
        super("#", new File("."));

        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
