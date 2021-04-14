/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.application;

import com.acidmanic.applicationpattern.ApplicationBase;
import com.acidmanic.applicationpattern.ApplicationService;
import com.acidmanic.applicationpattern.ServiceManager;
import com.acidmanic.lightweight.logger.Logger;

/**
 *
 * @author diego
 */
public class BrokerApplication extends ApplicationBase {

    private final Logger logger;
    private final BrokerResolver resolver;
    
    public BrokerApplication(ServiceManager serviceManager,
            Logger logger,
            BrokerResolver resolver) {
        super(serviceManager);
        this.logger = logger;
        this.resolver = resolver;
    }

    @Override
    protected void declareServices(ServiceManager manager) {

        resolver.resolveAllImplemented(ApplicationService.class)
                .forEach(service -> manager.declareService((ApplicationService) service));
    }

    @Override
    protected void beforeStartingServices(String[] args) {
        super.beforeStartingServices(args); 
        
        this.logger.log("Starting services");
    }

    @Override
    protected void afterServicesStarted(String[] args) {
        this.logger.log("All services has been started");
    }

    
    
    
    @Override
    public void dispose() {
    }

}
