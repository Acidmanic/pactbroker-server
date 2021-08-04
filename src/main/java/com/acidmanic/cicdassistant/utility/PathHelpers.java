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

import java.nio.file.Path;

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

    public PathRelation relation(Path first, Path second) {

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
            return PathRelation.ChildOf;
        }
        return PathRelation.ParentOf;
    }
}
