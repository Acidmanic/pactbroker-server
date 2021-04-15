/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.application;

import com.acidmanic.localpactbroker.commands.ApplicationSwitch;

/**
 *
 * @author diego
 */
public class ShutdownBus implements ShutdownDetect, ApplicationSwitch {

    private Runnable shutdownListener = () -> {
    };

    @Override
    public void registerShutdownListener(Runnable onShutdown) {
        this.shutdownListener = onShutdown;
    }

    @Override
    public void shutdown() {
        if (this.shutdownListener != null) {
            this.shutdownListener.run();
        }
    }
}
