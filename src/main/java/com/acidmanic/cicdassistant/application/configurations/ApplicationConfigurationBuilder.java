/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.application.configurations;

import com.acidmanic.cicdassistant.infrastructure.configuration.ConfigurationBuilder;

/**
 *
 * @author diego
 */
public class ApplicationConfigurationBuilder extends ConfigurationBuilder<Configurations> {

    public static ApplicationConfigurationBuilder instance = null;

    public static synchronized ApplicationConfigurationBuilder makeInstance() {
     
        if (instance == null) {
            instance = new ApplicationConfigurationBuilder();
        }
        return instance;
    }

    @Override
    protected Configurations createDefaults() {
        
        Configurations configurations = new Configurations();
        
        configurations.setMailSmtpServer("mail.example.com");
        
        configurations.setServicePort(8585);
        
        return configurations;
    }

}
