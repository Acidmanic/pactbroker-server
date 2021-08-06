/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functional;

import com.acidmanic.cicdassistant.wiki.convert.InMemoryResources;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author diego
 */
public class SaveInMemoryWikiStyles {

    public static void main(String[] args) {

        Path directory = Paths.get("debug").resolve("wiki-styles");

        if (!directory.toFile().exists()) {

            directory.toFile().mkdirs();
        }

        for (String name : InMemoryResources.NAMED_THEMES.keySet()) {

            Path file = directory.resolve(name+".css");
            
            String style = InMemoryResources.NAMED_THEMES.get(name);            
            
            try {
                Files.write(file, style.getBytes(), StandardOpenOption.CREATE);
                
            } catch (Exception e) {
            }
        }
    }
}
