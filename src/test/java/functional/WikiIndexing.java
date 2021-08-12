/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functional;

import com.acidmanic.cicdassistant.wiki.autoindexing.MarkdownWikiIndexTree;
import com.acidmanic.cicdassistant.wiki.autoindexing.WebNode;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author diego
 */
public class WikiIndexing {

    public static void main(String[] args) {

        File root = Paths.get("dev-workspace").resolve("wikiroot.dev").toFile();

        MarkdownWikiIndexTree index = new MarkdownWikiIndexTree(root);

        List<WebNode> heads = index.getHeads();

        List<WebNode> miscs = index.getMiscellaneousNodes();

        printMenue(heads, miscs);

    }

    private static void printDown(WebNode key, String indent) {

        System.out.println(indent + key.getKey());

        for (WebNode child : key.getReferences()) {

            printDown(child, indent + "|------");
        }
    }

    private static void printMenue(List<WebNode> heads, List<WebNode> miscs) {

        for (WebNode key : heads) {

            System.out.println("===================");

            printDown(key, "");
        }

        System.out.println("===================");
        System.out.println("Misc");
        System.out.println("-------------------");

        for (WebNode misc : miscs) {
            printDown(misc, "");
        }
    }
    
}
