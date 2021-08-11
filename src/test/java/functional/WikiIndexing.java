/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functional;

import java.io.File;
import java.nio.file.Paths;

/**
 *
 * @author diego
 */
public class WikiIndexing {

    public static void main(String[] args) {

        File root = Paths.get("dev-workspace").resolve("wikiroot.dev").toFile();

        Index index = new Index(root);

        index.indexDirectory();
    }
}
