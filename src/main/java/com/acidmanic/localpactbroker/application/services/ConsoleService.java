/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.application.services;

import com.acidmanic.applicationpattern.SyncLoopApplicationServiceBase;
import com.acidmanic.commandline.commands.Command;
import com.acidmanic.commandline.commands.CommandFactory;
import com.acidmanic.commandline.commands.TypeRegistery;
import com.acidmanic.commandline.commands.context.ExecutionContext;
import com.acidmanic.commandline.utility.LineParser;
import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.localpactbroker.commands.ApplicationContext;
import java.io.InputStream;
import java.util.Map;

/**
 *
 * @author diego
 */
public class ConsoleService extends SyncLoopApplicationServiceBase {

    private final InputStream inputStream;
    private StringBuilder inputBuffer;
    private final ExecutionContext context;
    private final TypeRegistery typeRegistery;

    public ConsoleService(Logger logger,
            ApplicationContext context,
            TypeRegistery typeRegistery) {
        super(logger, 10);
        this.inputStream = System.in;
        this.inputBuffer = new StringBuilder();
        this.context = context;
        this.typeRegistery = typeRegistery;
    }

    @Override
    protected void loopJob() {
        int value = 0;

        value = nonBlockingRead();
        if (value != -1) {
            if (value == 10) {
                deliver();
            } else {
                this.inputBuffer.append((char) value);
            }
        }
        threadWait(10);
    }

    @SuppressWarnings("UseSpecificCatch")
    private int nonBlockingRead() {
        try {
            if (inputStream.available() > 0) {
                return this.inputStream.read();
            }
        } catch (Exception e) {
        }
        return -1;
    }

    private void deliver() {
        String command = this.inputBuffer.toString();

        this.inputBuffer = new StringBuilder();

        CommandFactory factory = new CommandFactory(
                this.typeRegistery, 
                this.getLogger(),
                this.context);
        
        String[] args = new LineParser().analyseLine(command);
        
        Map<Command,String[]> commands = factory.make(args, true);
        
        commands.forEach((c,ar) -> c.execute(args));
    }

    private void threadWait(long delay) {
        try {
            Thread.sleep(delay);
        } catch (Exception e) {
        }
    }

}
