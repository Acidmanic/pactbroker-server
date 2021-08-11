/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.authorization;

import com.acidmanic.cicdassistant.utility.StringUtils;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

/**
 *
 * @author diego
 */
public class GitlabAuthorizationProvider implements AuthorizationProvider {

    private final String gitlabBaseUrl;

    public GitlabAuthorizationProvider(String gitlabBaseUrl) {

        String url = StringUtils.stripSides(gitlabBaseUrl, "", "/");

        this.gitlabBaseUrl = url;
    }

    @Override
    public boolean isAuthorized(RequestDataProvider requestDataProvider) {

        String gitlabSession = requestDataProvider.readCookie("_gitlab_session");
        
        if (!StringUtils.isNullOrEmpty(gitlabSession)) {

            String url = this.gitlabBaseUrl + "/api/v4/users";

            try {
                
                HttpResponse response = Unirest
                        .get(url)
                        .cookie("_gitlab_session", gitlabSession)
                        .asEmpty();

                if (response.isSuccess() && response.getStatus() != 403) {

                    return true;
                }
            } catch (Exception e) {
            }
        }
        return false;
    }

}
