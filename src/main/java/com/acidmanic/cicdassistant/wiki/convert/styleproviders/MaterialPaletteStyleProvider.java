/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.styleproviders;

import com.acidmanic.cicdassistant.html.theme.MaterialPaletteColors;
import com.acidmanic.cicdassistant.wiki.convert.style.HtmlStyleProvider;
import com.acidmanic.cicdassistant.wiki.convert.styleproviders.palettes.MaterialPalettes;
import com.acidmanic.cicdassistant.wiki.convert.styleproviders.palettes.NamedMaterialPalette;
import com.acidmanic.cicdassistant.wiki.convert.structure.MaterialBasedWikiStyleSheet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author diego
 */
public class MaterialPaletteStyleProvider implements HtmlStyleProvider {

    private static final HashMap<String, NamedMaterialPalette> palettes = new HashMap<>();
    private static final String defaultStyleName;

    static {

        for (NamedMaterialPalette palette : MaterialPalettes.ALL) {

            palettes.put(palette.getName(), palette);
        }
        defaultStyleName = MaterialPalettes.ALL[0].getName();
    }

    @Override
    public List<String> getNames() {

        return new ArrayList<>(palettes.keySet());
    }

    @Override
    public String getHeadInjectableHtml(String name) {

        NamedMaterialPalette palette = palettes.getOrDefault(name,
                new NamedMaterialPalette("No Style",
                        MaterialPaletteColors.accentColor));

        MaterialBasedWikiStyleSheet styleSheet = new MaterialBasedWikiStyleSheet(palette);

        return styleSheet.toString();
    }

    @Override
    public String getStyleFlagColorCode(String name) {

        if (palettes.containsKey(name)) {

            return palettes.get(name).getFlagColorCode();
        }
        return "#FFFFFF00";
    }

    @Override
    public String getDefaultName() {

        return defaultStyleName;
    }

}
