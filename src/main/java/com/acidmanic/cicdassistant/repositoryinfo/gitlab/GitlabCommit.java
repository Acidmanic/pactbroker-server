/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.repositoryinfo.gitlab;

import com.acidmanic.repositoryinfo.Commit;

/**
 *
 * @author diego
 */
public class GitlabCommit extends Commit {

    private String webUrl;

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

}
