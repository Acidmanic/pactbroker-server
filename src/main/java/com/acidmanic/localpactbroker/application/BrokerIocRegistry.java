/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.application;

import com.acidmanic.commandline.commands.TypeRegistery;
import com.acidmanic.localpactbroker.application.services.ConsoleService;
import com.acidmanic.localpactbroker.commands.Token;
import com.acidmanic.localpactbroker.storage.PactStorage;
import com.acidmanic.localpactbroker.storage.TokenStorage;
import com.acidmanic.utility.myoccontainer.Installer;
import com.acidmanic.utility.myoccontainer.Registerer;
import com.acidmanic.utility.myoccontainer.Resolver;
import com.acidmanic.utility.myoccontainer.lifetimemanagement.LifetimeType;

/**
 *
 * @author diego
 */
public class BrokerIocRegistry implements Installer {

    @Override
    public void configure(Registerer reg) {

        configureApplicationServices(reg);

        configureInfrastructureServices(reg);

        configureControllers(reg);

    }

    private void configureApplicationServices(Registerer reg) {
        reg.register().bindToSelf(ConsoleService.class).livesAsA(LifetimeType.Singleton);
    }

    private void configureInfrastructureServices(Registerer reg) {

        TypeRegistery commandsRegistery = new TypeRegistery();

        reg.register().bind(TypeRegistery.class).withBuilder(() -> commandsRegistery)
                .livesAsA(LifetimeType.Singleton);

        configureCommands(commandsRegistery);

        reg.register().bind(Resolver.class).withBuilder(() -> BrokerResolver.makeInstance());

        reg.register().bindToSelf(TokenStorage.class).livesAsA(LifetimeType.Transient);

        reg.register().bindToSelf(PactStorage.class).livesAsA(LifetimeType.Transient);

    }

    private void configureControllers(Registerer reg) {

    }

    private void configureCommands(TypeRegistery reg) {

        reg.registerClass(Token.class);
    }

}
