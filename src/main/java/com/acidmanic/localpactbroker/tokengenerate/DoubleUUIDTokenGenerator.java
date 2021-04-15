/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.tokengenerate;

import java.util.UUID;

/**
 *
 * @author diego
 */
public class DoubleUUIDTokenGenerator implements TokenGenerator{

    @Override
    public String generateToken() {
        
        return UUID.randomUUID().toString() + "." + UUID.randomUUID().toString();
    }
    
}
