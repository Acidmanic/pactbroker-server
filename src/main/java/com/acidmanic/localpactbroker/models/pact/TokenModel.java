/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.models.pact;

import java.util.UUID;

/**
 *
 * @author diego
 */
public class TokenModel {

    private String value;

    public TokenModel() {
        this.value = UUID.randomUUID().toString();
    }

    public TokenModel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
