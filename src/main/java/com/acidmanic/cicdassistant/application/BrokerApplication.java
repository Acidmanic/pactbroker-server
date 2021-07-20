/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.application;

import com.acidmanic.applicationpattern.ApplicationBase;
import com.acidmanic.applicationpattern.ApplicationService;
import com.acidmanic.applicationpattern.ServiceManager;
import com.acidmanic.cicdassistant.commands.ApplicationContext;
import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.cicdassistant.utility.KillFile;
import com.acidmanic.commandline.commands.Command;
import com.acidmanic.commandline.commands.CommandFactory;
import com.acidmanic.commandline.commands.TypeRegistery;
import java.util.Map;

/**
 *
 * @author diego
 */
public class BrokerApplication extends ApplicationBase {

    private final Logger logger;
    private final BrokerResolver resolver;
    private final TypeRegistery typeRegistery;
    private final ApplicationContext context;

    public BrokerApplication(ServiceManager serviceManager,
            Logger logger,
            BrokerResolver resolver,
            ShutdownDetect shutdownDetect,
            TypeRegistery typeRegistery,
            ApplicationContext context) {
        super(serviceManager);
        this.logger = logger;
        this.resolver = resolver;
        shutdownDetect.registerShutdownListener(() -> this.stop());
        this.typeRegistery = typeRegistery;
        this.context = context;
    }

    @Override
    protected void declareServices(ServiceManager manager) {

        resolver.resolveAllImplemented(ApplicationService.class)
                .forEach(service -> manager.declareService((ApplicationService) service));
    }

    @Override
    protected void beforeStartingServices(String[] args) {
        super.beforeStartingServices(args);

        CommandFactory factory = new CommandFactory(
                this.typeRegistery,
                this.logger,
                this.context
        );

        this.logger.log("Checking for pre start commands.");

        Map<Command, String[]> commands = factory.make(args, true);

        commands.forEach((c, ar) -> c.execute(args));
        
        if(!commands.isEmpty()){
            
            this.stop();
            
            System.exit(0);
            
            return;
        }

        this.logger.log("Starting services");

        new KillFile().delete();
    }

    @Override
    protected void afterServicesStarted(String[] args) {
        this.logger.log("All services has been started");
    }

    @Override
    public void dispose() {
    }

}
