/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.application.services;

import com.acidmanic.applicationpattern.SyncOrientedApplicationServiceBase;
import com.acidmanic.cicdassistant.application.configurations.ApplicationConfigurationBuilder;
import com.acidmanic.cicdassistant.application.configurations.Configurations;
import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.cicdassistant.utility.ResourceHelper;
import com.acidmanic.resteasywrapper.ControllersProvider;
import com.acidmanic.resteasywrapper.WebService;
import java.io.File;

/**
 *
 * @author diego
 */
public class BrokerWebService extends SyncOrientedApplicationServiceBase {

    private WebService webService;
    private final ControllersProvider controllersProvider;
    private final Configurations configurations;

    public BrokerWebService(ControllersProvider controllersProvider,
             Logger logger,
            ApplicationConfigurationBuilder configurationBuilder) {
        super(logger);
        this.controllersProvider = controllersProvider;
        this.configurations = configurationBuilder.readConfigurations();
    }

    @Override
    protected void performSyncAction() {

        File webDirectory = new ResourceHelper().getExecutionDirectory().toFile();

        webService = new WebService(getLogger(),
                this.configurations.getServicePort(),
                webDirectory,
                this.controllersProvider);

        webService.syncStart();
    }

    @Override
    protected void stopSyncAction() {
        if (webService != null) {
            webService.stop();
        }
        webService = null;
    }

}
