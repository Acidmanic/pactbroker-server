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

import java.io.File;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class JGit {

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
        }
        return false;
    }

    public boolean clone(String repo, String username, String password, File directory) {
        try {
            Git.cloneRepository()
                    .setDirectory(directory)
                    .setURI(repo)
                    .setCredentialsProvider(
                            new UsernamePasswordCredentialsProvider(username, password)
                    )
                    .call();
            return true;
        } catch (Exception e) {
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
        }
        return false;
    }
}
