/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.repositoryinfo;

import java.util.List;

/**
 *
 * @author diego
 */
public interface GitRepositoryInterface<TConfigurations> {

    void setConfigurations(TConfigurations configurations);

    List<Commit> getRepositoryCommits();

    Commit getSingleCommit(String hash);

}
