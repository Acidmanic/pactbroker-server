/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.authorization;

/**
 *
 * @author diego
 */
public interface RequestDataProvider {
 
    
    
    String readCookie(String name);
    String readHeader(String name);
    String readQuery(String name);
    
}
