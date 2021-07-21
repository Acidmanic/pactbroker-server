/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functional;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 *
 * @author diego
 */
public class TestDataProvider {
    
    private static final String DATA_FILE = "testdata.xml";

    public static Properties get() {
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream(DATA_FILE));

        } catch (Exception e) {
        }

        return properties;
    }

    public static void init() {
        Properties p = new Properties();

        p.setProperty("example-key", "example-value");

        try {
            p.storeToXML(new FileOutputStream(DATA_FILE), "Functional test data");

        } catch (Exception e) {
        }
    }
}
