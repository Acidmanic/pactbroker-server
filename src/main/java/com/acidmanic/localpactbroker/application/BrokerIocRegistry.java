/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.application;

import com.acidmanic.utility.myoccontainer.Installer;
import com.acidmanic.utility.myoccontainer.Registerer;

/**
 *
 * @author diego
 */
public class BrokerIocRegistry implements Installer{

    @Override
    public void configure(Registerer reg) {
        
        configureApplicationServices(reg);
        
        configureInfrastructureServices(reg);
        
        configureControllers(reg);
        
    }

    private void configureApplicationServices(Registerer reg) {
        
    }

    private void configureInfrastructureServices(Registerer reg) {
        
    }

    private void configureControllers(Registerer reg) {
        
    }
    
}
