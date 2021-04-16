/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.application.services;

import com.acidmanic.applicationpattern.SyncLoopApplicationServiceBase;
import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.localpactbroker.commands.ApplicationSwitch;
import com.acidmanic.localpactbroker.utility.KillFile;

/**
 *
 * @author diego
 */
public class KillFileService extends SyncLoopApplicationServiceBase{

    private final ApplicationSwitch applicationSwitch;
    
    public KillFileService(Logger logger,ApplicationSwitch applicationSwitch) {
        super(logger, 1000);
        this.applicationSwitch=applicationSwitch;
    }

    @Override
    protected void loopJob() {
        KillFile killfile = new KillFile();
        
        if(killfile.isPresent()){
            applicationSwitch.shutdown();
        }
    }
    
}
