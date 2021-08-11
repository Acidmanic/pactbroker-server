/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.authorization;

/**
 *
 * @author diego
 */
public interface AuthorizationProvider {

    boolean isAuthorized(RequestDataProvider requestDataProvider);
}
