/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functional;

import com.acidmanic.cicdassistant.html.StyleSheet;
import com.acidmanic.cicdassistant.html.theme.MaterialPalette;
import com.acidmanic.cicdassistant.html.theme.MaterialPaletteColors;
import com.acidmanic.cicdassistant.html.theme.MaterialStyle;
import com.acidmanic.cicdassistant.html.Style;

/**
 *
 * @author diego
 */
public class StyleSheetTests {

    public static void main(String[] args) {

        StyleSheet styleSheet = new StyleSheet();

        String palettePolymer = ""
                + "        --dark-primary-color:       #AFB42B;\n"
                + "        --default-primary-color:    #CDDC39;\n"
                + "        --light-primary-color:      #F0F4C3;\n"
                + "        --text-primary-color:       #212121;\n"
                + "        --accent-color:             #4CAF50;\n"
                + "        --primary-background-color: #F0F4C3;\n"
                + "        --primary-text-color:       #212121;\n"
                + "        --secondary-text-color:     #757575;\n"
                + "        --disabled-text-color:      #BDBDBD;\n"
                + "        --divider-color:            #BDBDBD;";

        MaterialPalette palette = MaterialPalette.fromPolymer(palettePolymer);

        MaterialStyle divStyle = new MaterialStyle(palette, "div.mashang");
        
        divStyle.addColorProperty("color", MaterialPaletteColors.primaryText);
        divStyle.addColorProperty("background-color", MaterialPaletteColors.lightPrimaryColor);

        
        styleSheet.addChild(divStyle);

        String html = styleSheet.toString();

        System.out.println(html);
        
        
        palette.get(MaterialPaletteColors.primaryText).setRed(0);
        palette.get(MaterialPaletteColors.primaryText).setGreen(1);
        palette.get(MaterialPaletteColors.primaryText).setBlue(0);

        System.out.println("tamper the pallete");
        
         html = styleSheet.toString();

        System.out.println(html);
    }
}
