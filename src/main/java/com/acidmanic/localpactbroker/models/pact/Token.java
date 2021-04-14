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
public class Token {

    private UUID value;

    public Token() {
        this.value = UUID.randomUUID();
    }

    public UUID getValue() {
        return value;
    }

    public void setValue(UUID value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

}
