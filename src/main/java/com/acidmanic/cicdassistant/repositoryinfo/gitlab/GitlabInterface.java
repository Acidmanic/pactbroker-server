/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.repositoryinfo.gitlab;

import com.acidmanic.repositoryinfo.Commit;
import com.acidmanic.repositoryinfo.GitRepositoryInterface;
import java.util.ArrayList;
import java.util.List;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.jackson.JacksonObjectMapper;

/**
 *
 * @author diego
 */
public class GitlabInterface implements GitRepositoryInterface<GitlabConfiguration> {

    private GitlabConfiguration configurations;

    @Override
    public List<Commit> getRepositoryCommits() {

        GitlabCommitDto[] commitsReponse = requestAllCommits();

        ArrayList<Commit> result = new ArrayList<>();

        for (GitlabCommitDto dto : commitsReponse) {

            Commit commit = mapCommit(dto);

            result.add(commit);

        }
        return result;
    }

    private Commit mapCommit(GitlabCommitDto dto) {

        GitlabCommit gitlabCommit = new GitlabCommit();

        gitlabCommit.setAuthorEmail(dto.getAuthor_email());
        gitlabCommit.setAuthorName(dto.getAuthor_name());
        gitlabCommit.setCreatedAt(dto.getCommitted_date());
        gitlabCommit.setId(dto.getId());
        gitlabCommit.setMessage(dto.getMessage());
        gitlabCommit.setShortId(dto.getShort_id());
        gitlabCommit.setTitle(dto.getTitle());
        gitlabCommit.setWebUrl(dto.getWeb_url());

        return gitlabCommit;
    }

    private GitlabCommitDto[] requestAllCommits() {

        String url = configurations.getGitlabBaseUrl();

        if (!url.endsWith("/")) {
            url += "/";
        }

        url = url + "api/v4/projects/" + configurations.getProjectId() + "/repository/commits";

        Unirest.config().setObjectMapper(new JacksonObjectMapper());

        HttpResponse<GitlabCommitDto[]> response = null;

        try {
            response = Unirest
                    .get(url)
                    .header("Authorization", "Bearer " + configurations.getToken())
                    .queryString("all", true)
                    .queryString("first_parent", false)
                    .queryString("per_page", 100000)
                    .asObject(GitlabCommitDto[].class);

            if (response.isSuccess()) {

                GitlabCommitDto[] body = response.getBody();

                return body;
            }

        } catch (Exception e) {

        }

        return new GitlabCommitDto[]{};
    }

    @Override
    public void setConfigurations(GitlabConfiguration configurations) {
        this.configurations = configurations;
    }

    private GitlabCommitDto requestCommit(String hash) {

        String url = configurations.getGitlabBaseUrl();

        if (!url.endsWith("/")) {
            url += "/";
        }

        url = url + "api/v4/projects/" + configurations.getProjectId() + "/repository/commits/" + hash;

        Unirest.config().setObjectMapper(new JacksonObjectMapper());

        HttpResponse<GitlabCommitDto> response = null;

        try {
            response = Unirest
                    .get(url)
                    .header("Authorization", "Bearer " + configurations.getToken())
                    .queryString("type", "all")
                    .asObject(GitlabCommitDto.class);

            if (response.isSuccess()) {

                GitlabCommitDto body = response.getBody();

                return body;
            }

        } catch (Exception e) {

        }

        return null;
    }

    @Override
    public Commit getSingleCommit(String hash) {

        GitlabCommitDto dto = requestCommit(hash);

        if (dto == null) {

            return null;
        }
        return mapCommit(dto);
    }

}
