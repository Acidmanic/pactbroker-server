/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.application;

import com.acidmanic.applicationpattern.Application;
import com.acidmanic.applicationpattern.DefaultServiceManager;
import com.acidmanic.applicationpattern.ServiceManager;
import com.acidmanic.commandline.commands.Help;
import com.acidmanic.commandline.commands.TypeRegistery;
import com.acidmanic.lightweight.logger.ConsoleLogger;
import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.localpactbroker.application.services.BrokerWebService;
import com.acidmanic.localpactbroker.application.services.ConsoleService;
import com.acidmanic.localpactbroker.application.services.web.BrokerControllerProvider;
import com.acidmanic.localpactbroker.commands.ApplicationContext;
import com.acidmanic.localpactbroker.commands.ApplicationSwitch;
import com.acidmanic.localpactbroker.commands.Exit;
import com.acidmanic.localpactbroker.commands.Token;
import com.acidmanic.localpactbroker.controllers.PactController;
import com.acidmanic.localpactbroker.storage.PactStorage;
import com.acidmanic.localpactbroker.storage.StorageFileConfigs;
import com.acidmanic.localpactbroker.storage.TokenStorage;
import com.acidmanic.localpactbroker.tokengenerate.DoubleUUIDTokenGenerator;
import com.acidmanic.localpactbroker.tokengenerate.TokenGenerator;
import com.acidmanic.resteasywrapper.ControllersProvider;
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

        reg.register().bindToSelf(BrokerWebService.class).livesAsA(LifetimeType.Singleton);
    }

    private void configureInfrastructureServices(Registerer reg) {

        TypeRegistery commandsRegistery = new TypeRegistery();

        reg.register().bind(TypeRegistery.class).withBuilder(() -> commandsRegistery)
                .livesAsA(LifetimeType.Singleton);

        configureCommands(commandsRegistery);

        reg.register().bind(Resolver.class).withBuilder(() -> BrokerResolver.makeInstance());

        reg.register().bind(BrokerResolver.class).withBuilder(() -> BrokerResolver.makeInstance());

        reg.register().bindToSelf(TokenStorage.class)
                .livesAsA(LifetimeType.Transient);

        reg.register().bindToSelf(PactStorage.class)
                .livesAsA(LifetimeType.Transient);

        reg.register().bind(ServiceManager.class).to(DefaultServiceManager.class)
                .livesAsA(LifetimeType.Singleton);

        reg.register().bind(Logger.class).to(ConsoleLogger.class)
                .livesAsA(LifetimeType.Singleton);

        reg.register().bind(Application.class).to(BrokerApplication.class)
                .livesAsA(LifetimeType.Transient);

        reg.register().bindToSelf(ApplicationContext.class)
                .livesAsA(LifetimeType.Transient);

        reg.register().bind(StorageFileConfigs.class).to(BrokerStorageConfigs.class)
                .livesAsA(LifetimeType.Singleton);

        reg.register().bind(TokenGenerator.class).to(DoubleUUIDTokenGenerator.class)
                .livesAsA(LifetimeType.Transient);

        reg.register().bind(ControllersProvider.class).to(BrokerControllerProvider.class)
                .livesAsA(LifetimeType.Transient);

        ShutdownBus bus = new ShutdownBus();

        reg.register().bind(ApplicationSwitch.class).withBuilder(() -> bus)
                .livesAsA(LifetimeType.Singleton);

        reg.register().bind(ShutdownDetect.class).withBuilder(() -> bus)
                .livesAsA(LifetimeType.Singleton);

    }

    private void configureControllers(Registerer reg) {
        reg.register().bindToSelf(PactController.class)
                .livesAsA(LifetimeType.Singleton);
    }

    private void configureCommands(TypeRegistery reg) {

        reg.registerClass(Token.class);
        reg.registerClass(Help.class);
        reg.registerClass(Exit.class);
    }

}
