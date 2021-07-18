/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.application.services;

import com.acidmanic.applicationpattern.SyncOrientedApplicationServiceBase;
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

    public BrokerWebService(ControllersProvider controllersProvider, Logger logger) {
        super(logger);
        this.controllersProvider = controllersProvider;
    }

    @Override
    protected void performSyncAction() {

        File webDirectory = new ResourceHelper().getExecutionDirectory().toFile();

        webService = new WebService(getLogger(),
                 8585, webDirectory,
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
