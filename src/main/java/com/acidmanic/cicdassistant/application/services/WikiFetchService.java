/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.application.services;

import com.acidmanic.applicationpattern.SyncLoopApplicationServiceBase;
import com.acidmanic.cicdassistant.application.configurations.Configurations;
import com.acidmanic.cicdassistant.services.WikiRepoStatus;
import com.acidmanic.cicdassistant.utility.JGit;
import com.acidmanic.cicdassistant.utility.ResourceHelper;
import com.acidmanic.cicdassistant.wiki.autoindexing.MarkdownWikiIndexTree;
import com.acidmanic.lightweight.logger.Logger;
import java.io.File;
import java.util.Date;

/**
 *
 * @author diego
 */
public class WikiFetchService extends SyncLoopApplicationServiceBase {

    private final WikiRepoStatus wikiRepoStatus;
    private final Configurations configurations;
    private Date lastFetch = new Date(0);// makes it to fetch on first run
    private File wikiDirectory;

    public WikiFetchService(Logger logger,
            WikiRepoStatus wikiRepoStatus,
            Configurations configurations) {
        super(logger, 5000);
        this.wikiRepoStatus = wikiRepoStatus;
        this.configurations = configurations;

    }

    private void checkDirectory() {
        //TODO: unify this with wiki router
        this.wikiDirectory = new ResourceHelper()
                .getExecutionDirectory()
                .resolve("wikiroot")
                .toAbsolutePath()
                .normalize()
                .toFile();
    }

    @Override
    protected void loopJob() {

        if (shouldFetch()) {

            this.wikiRepoStatus.setBusy();
            
            checkDirectory();

            performWikiFetch();
            
            updateIndex();

            this.wikiRepoStatus.setReady();

            this.wikiRepoStatus.setFetchSatisfied();
        }
    }

    // declare fetching strategy using this function
    private boolean shouldFetch() {

        long millisecondsFromLastFetch = new Date().getTime() - this.lastFetch.getTime();

        long elpassedMinutes = millisecondsFromLastFetch / (60000);

        if (elpassedMinutes >= configurations.getWikiConfigurations().getAutoRefetchMinutes()) {

            return true;
        }
        return this.wikiRepoStatus.anyFetchDemand();
    }

    private void performWikiFetch() {

        JGit git = new JGit(getLogger());

        if (!wikiDirectory.exists()) {

            wikiDirectory.mkdirs();
        }

        if (!wikiDirectory.toPath().resolve(".git").toFile().exists()) {

            boolean success = git.clone(configurations.getWikiConfigurations().getRemote(),
                    configurations.getWikiConfigurations().getUsername(),
                    configurations.getWikiConfigurations().getPassword(),
                    wikiDirectory);

            if (success) {
                getLogger().log("Clonned successfully.");
            } else {
                getLogger().warning("Problem cloning the repository");
            }

        } else {

            boolean success = git.pull("origin",
                    configurations.getWikiConfigurations().getUsername(),
                    configurations.getWikiConfigurations().getPassword(),
                    configurations.getWikiConfigurations().getWikiBranch(),
                    wikiDirectory);

            if (success) {
                getLogger().log("Local Wiki Repository Updated Successfully");
            } else {
                getLogger().warning("Problem updating the local wiki repository");
            }

        }
        this.lastFetch = new Date();
    }

    private void updateIndex() {
        
        MarkdownWikiIndexTree indexTree = new MarkdownWikiIndexTree(this.wikiDirectory);
        
        this.wikiRepoStatus.setIndexTree(indexTree);
        
        getLogger().log("Index Updated: " + indexTree.getTotalNodesCount() + " nodes in total.");
    }

}
