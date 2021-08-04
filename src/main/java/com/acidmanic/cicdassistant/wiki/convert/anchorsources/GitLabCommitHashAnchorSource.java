/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.anchorsources;

import com.acidmanic.cicdassistant.repositoryinfo.gitlab.GitlabCommit;
import com.acidmanic.cicdassistant.repositoryinfo.gitlab.GitlabConfigurations;
import com.acidmanic.cicdassistant.repositoryinfo.gitlab.GitlabInterface;
import com.acidmanic.cicdassistant.utility.CommonRegExes;
import com.acidmanic.cicdassistant.utility.StringUtils;
import com.acidmanic.lightweight.logger.SilentLogger;
import com.acidmanic.repositoryinfo.Commit;
import com.acidmanic.wiki.convert.autolink.Anchor;

/**
 *
 * @author diego
 */
public class GitLabCommitHashAnchorSource extends RegexStringExtractorAnchorSource {

    private final KeyNormalizingDataFetchAnchorCache cache;
    private final GitlabConfigurations configurations;

    public GitLabCommitHashAnchorSource(GitlabConfigurations configurations) {

        this.cache = new KeyNormalizingDataFetchAnchorCache(
                hash -> fetchHashAsAnchor(hash),
                hash -> normalizeHash(hash),
                "gitlab-hashes-cache",
                new SilentLogger());
        this.configurations = configurations;
    }

    @Override
    protected Anchor createAnchor(String hash) {

        return cache.get(hash);
    }

    private Anchor fetchHashAsAnchor(String hash) {

        GitlabInterface ginterface = new GitlabInterface();

        ginterface.setConfigurations(configurations);

        Commit commit = ginterface.getSingleCommit(hash);

        if (commit != null) {

            GitlabCommit gitlabCommit = (GitlabCommit) commit;

            Anchor anchor = new Anchor();

            anchor.setHref(gitlabCommit.getWebUrl());
            anchor.setText(gitlabCommit.getShortId());
            anchor.setTitle(gitlabCommit.getTitle());

            return anchor;
        }
        return null;
    }

    private static String normalizeHash(String hash) {

        if (StringUtils.isNullOrEmpty(hash)) {
            return "";
        }
        return hash.toLowerCase();
    }

    @Override
    protected String getRegEx() {

        return CommonRegExes.SHA1_REGEX;
    }

}
