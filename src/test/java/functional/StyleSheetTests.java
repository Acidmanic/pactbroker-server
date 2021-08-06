/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functional;

import com.acidmanic.cicdassistant.html.StyleSheet;
import com.acidmanic.cicdassistant.html.theme.MaterialPalette;
import com.acidmanic.cicdassistant.wiki.convert.stylesheet.WikiStyleSheet;

/**
 *
 * @author diego
 */
public class StyleSheetTests {

    public static void main(String[] args) {

        String palettePolymer = ""
                + "        --dark-primary-color:       #303F9F;\n"
                + "        --default-primary-color:    #3F51B5;\n"
                + "        --light-primary-color:      #C5CAE9;\n"
                + "        --text-primary-color:       #FFFFFF;\n"
                + "        --accent-color:             #9E9E9E;\n"
                + "        --primary-background-color: #C5CAE9;\n"
                + "        --primary-text-color:       #212121;\n"
                + "        --secondary-text-color:     #757575;\n"
                + "        --disabled-text-color:      #BDBDBD;\n"
                + "        --divider-color:            #BDBDBD;";

        palettePolymer = ""
                + "        --dark-primary-color:       #00796B;\n"
                + "        --default-primary-color:    #009688;\n"
                + "        --light-primary-color:      #B2DFDB;\n"
                + "        --text-primary-color:       #FFFFFF;\n"
                + "        --accent-color:             #9E9E9E;\n"
                + "        --primary-background-color: #B2DFDB;\n"
                + "        --primary-text-color:       #212121;\n"
                + "        --secondary-text-color:     #757575;\n"
                + "        --disabled-text-color:      #BDBDBD;\n"
                + "        --divider-color:            #BDBDBD;";

        MaterialPalette palette = MaterialPalette.fromPolymer(palettePolymer);

        StyleSheet sheet = new WikiStyleSheet(palette);

        String html = sheet.toString();

        System.out.println(html);

    }
}
