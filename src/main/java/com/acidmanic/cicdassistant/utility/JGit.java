/* 
 * The MIT License
 *
 * Copyright 2019 Mani Moayedi (acidmanic.moayedi@gmail.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.acidmanic.cicdassistant.utility;

import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.lightweight.logger.SilentLogger;
import java.io.File;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class JGit {

    private final Logger logger;

    public JGit(Logger logger) {
        this.logger = logger;
    }

    public JGit() {
        this(new SilentLogger());
    }

    public boolean acceptLocalChanges(File directory, String commitMessage) {
        Git git = tryGetGit(directory);
        if (git != null) {
            try {
                git.add()
                        .addFilepattern(".")
                        .setUpdate(true)
                        .call();
                git.add()
                        .addFilepattern(".")
                        .call();
                git.commit().setMessage(commitMessage).call();
                return true;
            } catch (Exception ex) {
                logException(ex, "commiting changes");
            }
        }
        return false;
    }

    public boolean acceptLocalChanges(String directory, String commitMessage) {
        return acceptLocalChanges(new File(directory), commitMessage);
    }

    private Git tryGetGit(File directory) {
        try {
            return Git.open(directory);
        } catch (Exception e) {
            logException(e, "creating git object");
        }
        return null;
    }

    public boolean clone(String repo, File directory) {
        try {
            Git.cloneRepository()
                    .setDirectory(directory)
                    .setURI(repo)
                    .call();
            return true;
        } catch (Exception e) {
            logException(e, "cloning repository");
        }
        return false;
    }

    public boolean clone(String repo, String username, String password, File directory) {
        try {
            CloneCommand gitClone
                    = Git.cloneRepository()
                            .setDirectory(directory)
                            .setURI(repo);
            if (!StringUtils.isNullOrEmpty(username)) {

                gitClone.setCredentialsProvider(
                        new UsernamePasswordCredentialsProvider(username, password));
            }

            gitClone.call();
            return true;
        } catch (Exception e) {
            logException(e, "cloning repository");
        }
        return false;
    }

    public boolean push(String remote, File directory) {
        Git git = tryGetGit(directory);

        try {
            git.push().setPushAll()
                    .setRemote(remote)
                    .call();
            return true;
        } catch (Exception e) {
            logException(e, "Pushing changes");
        }
        return false;
    }

    public boolean push(String remote, String username, String password, File directory) {

        Git git = tryGetGit(directory);

        try {
            git.push().setPushAll()
                    .setRemote(remote)
                    .setCredentialsProvider(
                            new UsernamePasswordCredentialsProvider(username, password)
                    ).call();
            return true;
        } catch (Exception e) {
            logException(e, "Pushing changes");
        }
        return false;
    }

    public boolean pull(String remote, String username, String password, String branch, File directory) {

        Git git = tryGetGit(directory);

        try {
            PullCommand gitPull
                    = git.pull()
                            .setRemoteBranchName(branch)
                            .setRemote(remote);

            if (!StringUtils.isNullOrEmpty(username)) {
                gitPull.setCredentialsProvider(
                        new UsernamePasswordCredentialsProvider(username, password));
            }
            gitPull.call();
            return true;
        } catch (Exception e) {
            logException(e, "Pulling from repo");
        }
        return false;
    }

    private void logException(Exception ex, String underGoingTask) {

        this.logger.error("Error " + underGoingTask + ": " + ex.getClass().getSimpleName());
        this.logger.error(ex.getMessage());
    }
}
