/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.application;

import com.acidmanic.applicationpattern.Application;
import com.acidmanic.utility.myoccontainer.Resolver;

/**
 *
 * @author diego
 */
public class Main {
    
    
    public static void main(String[] args){

        Resolver resolver = BrokerResolver.makeInstance();

        Application application
                = resolver.tryResolve(Application.class);

        application.start(args, false);

        application.dispose();
    }
}
