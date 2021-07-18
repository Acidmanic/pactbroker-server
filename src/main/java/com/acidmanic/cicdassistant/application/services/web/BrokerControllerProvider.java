/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.application.services.web;

import com.acidmanic.cicdassistant.application.BrokerResolver;
import com.acidmanic.resteasywrapper.ControllersProvider;
import java.util.List;

/**
 *
 * @author diego
 */
public class BrokerControllerProvider implements ControllersProvider {

    private final BrokerResolver resolver;

    public BrokerControllerProvider(BrokerResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public List<Object> provideAllControllerInstances() {

        List<Object> controllers = resolver
                .resolveAllAnnotatedBy(Controller.class);

        return controllers;
    }

}
