/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.infrastructure.contracts;

/**
 *
 * @author diego
 */
public interface SshSessionParameters {

    String getUsername();

    String getPassword();

    String getHost();

    int getPort();
}
