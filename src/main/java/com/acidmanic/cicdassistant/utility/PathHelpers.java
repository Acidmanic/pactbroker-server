/*
 * The MIT License
 *
 * Copyright 2021 diego.
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
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author diego
 */
public class PathHelpers {

    public enum PathRelation {
        Sibling,
        ParentOf,
        ChildOf,
        NotRelated,
        Identical,
        Diverged
    }

    public Path getFileSystemRoot() {

        return getFileSystemRootOrDefault(null);
    }

    /**
     * This method returns the root (or the main root) of current file system.
     * If it fails to retrieve a root for current file system, it will return
     * the given default value.
     *
     * @param defaultPath
     * @return
     */
    public Path getFileSystemRootOrDefault(Path defaultPath) {

        File[] roots = File.listRoots();

        if (roots != null && roots.length > 0) {

            return roots[0].toPath();
        }
        return defaultPath;
    }

    /**
     * This method will determine the relation of two given links either they
     * are absolute or relative. For a correct comparison, it first makes both
     * links absolute if any of them are relative, based on given current
     * directory. The given currentDirectory parameter, is considered to be and
     * absolute path, even if it's not the method will work correctly.
     *
     * @param first
     * @param second
     * @param currentDirectory
     * @return
     */
    public PathRelation relation(Path first, Path second, Path currentDirectory) {

        if (!currentDirectory.isAbsolute()) {

            currentDirectory = currentDirectory.toAbsolutePath().normalize();
        }

        if (!first.isAbsolute()) {

            first = currentDirectory.resolve(first);
        }

        if (!second.isAbsolute()) {

            second = currentDirectory.resolve(second);
        }
        return relation(first, second);
    }

    /**
     * This method determines the relation between two given links, assuming
     * they both are absolute or both are relative.
     *
     * @param first
     * @param second
     * @return
     */
    public PathRelation relation(Path first, Path second) {

        Path resolved;

        resolved = first.resolve(second);

        if (resolved.equals(second)) {

            return PathRelation.ParentOf;
        }
        resolved = second.resolve(first);

        if (resolved.equals(first)) {

            return PathRelation.ChildOf;
        }
        return relationOfRelativePaths(first, second);
    }

    /**
     * This method determines the relation between two given links, assuming
     * they both are relative.
     *
     * @param first
     * @param second
     * @return
     */
    public PathRelation relationOfRelativePaths(Path first, Path second) {

        int firstCount = first.getNameCount();

        int secondCount = second.getNameCount();

        int minCount = Math.min(firstCount, secondCount);

        int commonSectionsCount = 0;

        for (int i = 0; i < minCount; i++) {

            String firstName = first.getName(i).toString();
            String secondName = second.getName(i).toString();

            if (firstName.compareTo(secondName) != 0) {

                break;
            } else {

                commonSectionsCount = i + 1;
            }
        }
        if (commonSectionsCount == 0) {

            return PathRelation.NotRelated;
        }
        if (firstCount == secondCount) {

            if (firstCount == commonSectionsCount) {

                return PathRelation.Identical;
            }
            if (firstCount - 1 == commonSectionsCount) {

                return PathRelation.Sibling;
            }
            return PathRelation.Diverged;
        }
        if (firstCount < secondCount) {
            // First is parent of second
            return PathRelation.ParentOf;
        }
        // First is child of second
        return PathRelation.ChildOf;
    }
}
