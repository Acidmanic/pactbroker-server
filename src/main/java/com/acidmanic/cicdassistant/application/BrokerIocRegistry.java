/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.application;

import com.acidmanic.applicationpattern.Application;
import com.acidmanic.applicationpattern.DefaultServiceManager;
import com.acidmanic.applicationpattern.ServiceManager;
import com.acidmanic.cicdassistant.application.configurations.ApplicationConfigurationBuilder;
import com.acidmanic.cicdassistant.application.configurations.Configurations;
import com.acidmanic.commandline.commands.Help;
import com.acidmanic.commandline.commands.TypeRegistery;
import com.acidmanic.lightweight.logger.ConsoleLogger;
import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.cicdassistant.application.services.BrokerWebService;
import com.acidmanic.cicdassistant.application.services.ConsoleService;
import com.acidmanic.cicdassistant.application.services.KillFileService;
import com.acidmanic.cicdassistant.application.services.web.BrokerControllerProvider;
import com.acidmanic.cicdassistant.commands.ApplicationContext;
import com.acidmanic.cicdassistant.commands.ApplicationSwitch;
import com.acidmanic.cicdassistant.commands.Exit;
import com.acidmanic.cicdassistant.commands.Token;
import com.acidmanic.cicdassistant.controllers.ArtifactsController;
import com.acidmanic.cicdassistant.controllers.BadgesController;
import com.acidmanic.cicdassistant.controllers.MailController;
import com.acidmanic.cicdassistant.controllers.PactController;
import com.acidmanic.cicdassistant.controllers.VerificationResultController;
import com.acidmanic.cicdassistant.infrastructure.configuration.ConfigurationBuilder;
import com.acidmanic.cicdassistant.services.ArtifactManager;
import com.acidmanic.cicdassistant.services.HtmlTemplateManager;
import com.acidmanic.cicdassistant.services.PactsManagerService;
import com.acidmanic.cicdassistant.services.SmtpClient;
import com.acidmanic.cicdassistant.storage.BadgeStorage;
import com.acidmanic.cicdassistant.storage.PactMapStorage;
import com.acidmanic.cicdassistant.storage.PactStorage;
import com.acidmanic.cicdassistant.storage.StorageFileConfigs;
import com.acidmanic.cicdassistant.storage.TokenStorage;
import com.acidmanic.cicdassistant.tokengenerate.DoubleUUIDTokenGenerator;
import com.acidmanic.cicdassistant.tokengenerate.TokenGenerator;
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

        

        configureInfrastructureServices(reg);

        configureRepositories(reg);

        configureControllers(reg);
        
        configureApplicationServices(reg);

    }

    private void configureApplicationServices(Registerer reg) {
        reg.register().bindToSelf(ConsoleService.class).livesAsA(LifetimeType.Singleton);

        reg.register().bindToSelf(BrokerWebService.class).livesAsA(LifetimeType.Singleton);

        reg.register().bindToSelf(KillFileService.class).livesAsA(LifetimeType.Singleton);
    }

    private void configureInfrastructureServices(Registerer reg) {

        TypeRegistery commandsRegistery = new TypeRegistery();

        reg.register().bind(TypeRegistery.class).withBuilder(() -> commandsRegistery)
                .livesAsA(LifetimeType.Singleton);

        configureCommands(commandsRegistery);

        reg.register().bind(Resolver.class).withBuilder(() -> BrokerResolver.makeInstance());

        reg.register().bind(BrokerResolver.class).withBuilder(() -> BrokerResolver.makeInstance());

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

        reg.register().bindToSelf(PactsManagerService.class).livesAsA(LifetimeType.Singleton);

        reg.register().bind(SmtpClient.class)
                .withBuilder(() -> new SmtpClient(
                ApplicationConfigurationBuilder
                        .makeInstance()
                        .readConfigurations()
                        .getMailSmtpServer()))
                .livesAsA(LifetimeType.Transient);

        reg.register().bind(Configurations.class)
                .withBuilder(() -> ApplicationConfigurationBuilder
                .makeInstance()
                .readConfigurations())
                .livesAsA(LifetimeType.Transient);
        
        reg.register().bind(ApplicationConfigurationBuilder.class)
                .withBuilder(() -> ApplicationConfigurationBuilder
                .makeInstance())
                .livesAsA(LifetimeType.Transient);

        reg.register().bindToSelf(HtmlTemplateManager.class);

        reg.register().bindToSelf(ArtifactManager.class);

    }

    private void configureControllers(Registerer reg) {
        reg.register().bindToSelf(PactController.class)
                .livesAsA(LifetimeType.Singleton);

        reg.register().bindToSelf(BadgesController.class)
                .livesAsA(LifetimeType.Singleton);

        reg.register().bindToSelf(VerificationResultController.class)
                .livesAsA(LifetimeType.Singleton);

        reg.register().bindToSelf(MailController.class)
                .livesAsA(LifetimeType.Singleton);

        reg.register().bindToSelf(ArtifactsController.class)
                .livesAsA(LifetimeType.Singleton);
    }

    private void configureCommands(TypeRegistery reg) {

        reg.registerClass(Token.class);
        reg.registerClass(Help.class);
        reg.registerClass(Exit.class);
    }

    private void configureRepositories(Registerer reg) {
        reg.register().bindToSelf(TokenStorage.class)
                .livesAsA(LifetimeType.Transient);

        reg.register().bindToSelf(PactStorage.class)
                .livesAsA(LifetimeType.Transient);

        reg.register().bindToSelf(BadgeStorage.class)
                .livesAsA(LifetimeType.Transient);

        reg.register().bindToSelf(PactMapStorage.class)
                .livesAsA(LifetimeType.Transient);
    }

}
